package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.controllers.api.WeatherDataController;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherDataControllerImpl implements WeatherDataController {

    private final WeatherDataService weatherDataService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Autowired
    public WeatherDataControllerImpl(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @Override
    public WeatherDataTo getCurrentWeather(String city) {
        return weatherDataService.getCurrentWeather(city, apiKey, apiUrl);
    }

    @Override
    public WeatherDataTo getWeatherForecast(String city, int days) {
        return weatherDataService.getWeatherForecast(city, apiKey, days, apiUrl);
    }

    @Override
    public List<WeatherDataTo> getLastSearches() {
        return weatherDataService.getLastSearches();
    }

}
