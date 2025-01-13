package com.nubisoft.nubiweather.services;

import com.nubisoft.nubiweather.mappers.WeatherDataMapper;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.repositories.ForecastDayRepository;
import com.nubisoft.nubiweather.repositories.WeatherDataRepository;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public WeatherDataTo getCurrentWeather(String city, String apiKey) {
        validateInputParameters(city, apiKey);
        String url = "http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no".formatted(apiKey, city);
        return getResponseEntity(url);
    }

    @Override
    public WeatherDataTo getWeatherForecast(String city, String apiKey, int days) {
        validateInputParameters(city, apiKey);
        String url = "http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%s&aqi=no&alerts=no".formatted(apiKey, city, days);
        return getResponseEntity(url);
    }

    private WeatherDataTo getResponseEntity(String url) {
        WeatherDataTo weatherInfoTo = restTemplate.getForObject(url, WeatherDataTo.class);
            keepLastTenSearches(weatherInfoTo);
        return weatherInfoTo;
    }

    private void validateInputParameters(String city, String apiKey) {
        if (city == null || apiKey == null) {
            throw new NullPointerException();
        }
    }

    private void keepLastTenSearches(WeatherDataTo weatherInfoTo) {
        deleteSearchData();
        saveSearchData(weatherInfoTo);
    }

    private void deleteSearchData() {
        // Note for reviewer: I did removal mechanism based on lowest id, but if createDate parameter was there, I would use it instead
        long numberOfSearchesSaved = weatherDataRepository.count();
        if (numberOfSearchesSaved >= 10) {
            Long lowestId = weatherDataRepository.findLowestId();
            forecastDayRepository.deleteByWeatherDataId(lowestId);
            weatherDataRepository.deleteById(lowestId);
        }
    }

    private void saveSearchData(WeatherDataTo weatherInfoTo) {
        weatherDataRepository.save(WeatherDataMapper.mapToEntity(weatherInfoTo));
    }
}
