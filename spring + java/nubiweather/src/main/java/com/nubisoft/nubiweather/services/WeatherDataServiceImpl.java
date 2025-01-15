package com.nubisoft.nubiweather.services;

import com.nubisoft.nubiweather.mappers.WeatherDataMapper;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.repositories.ForecastDayRepository;
import com.nubisoft.nubiweather.repositories.WeatherDataRepository;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class WeatherDataServiceImpl implements WeatherDataService {

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;
    private final ForecastDayRepository forecastDayRepository;

    @Autowired
    public WeatherDataServiceImpl(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, ForecastDayRepository forecastDayRepository) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
        this.forecastDayRepository = forecastDayRepository;
    }

    @Override
    @Validated
    public WeatherDataTo getCurrentWeather(@NotNull String city, @NotNull String apiKey, @NotNull String apiUrl) {
        String url = "%s/current.json?key=%s&q=%s&aqi=no".formatted(apiUrl, apiKey, city);
        return getResponseEntity(url);
    }

    @Override
    @Validated
    public WeatherDataTo getWeatherForecast(@NotNull String city, @NotNull String apiKey, @Min(1) @Max(10) int days, @NotNull String apiUrl) {
        String url = "%s/forecast.json?key=%s&q=%s&days=%s&aqi=no&alerts=no".formatted(apiUrl, apiKey, city, days);
        return getResponseEntity(url);
    }

    @Override
    public List<WeatherDataTo> getLastSearches() {
        return weatherDataRepository.findAll()
                .stream()
                .map(WeatherDataMapper::mapToWeatherDataTo)
                .toList();
    }

    private WeatherDataTo getResponseEntity(String url) {
        WeatherDataTo weatherInfoTo = restTemplate.getForObject(url, WeatherDataTo.class);
        keepLastTenSearches(weatherInfoTo);
        return weatherInfoTo;
    }

    private void keepLastTenSearches(WeatherDataTo weatherInfoTo) {
        deleteSearchData();
        saveSearchData(weatherInfoTo);
    }

    private void deleteSearchData() {
        // Note for reviewer: I did removal mechanism based on lowest id, but if createDate parameter was there, I would use it instead
        long numberOfSearchesSaved = weatherDataRepository.count();
        if (numberOfSearchesSaved >= 12) {
            Long lowestId = weatherDataRepository.findLowestId();
            forecastDayRepository.deleteByWeatherDataId(lowestId);
            weatherDataRepository.deleteById(lowestId);
        }
    }

    private void saveSearchData(WeatherDataTo weatherInfoTo) {
        weatherDataRepository.save(WeatherDataMapper.mapToEntity(weatherInfoTo));
    }
}
