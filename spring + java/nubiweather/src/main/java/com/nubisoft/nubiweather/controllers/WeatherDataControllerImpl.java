package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.controllers.api.WeatherDataController;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherDataControllerImpl implements WeatherDataController {

    private final WeatherDataService weatherDataService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherDataControllerImpl(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @Override
    @GetMapping("/current/{city}")
    public WeatherDataTo getCurrentWeather(@PathVariable String city) {
        return weatherDataService.getCurrentWeather(city, apiKey);
    }

    @Override
    @GetMapping("/forecast/{city}/{days}")
    public WeatherDataTo getWeatherForecast(@PathVariable String city, @PathVariable("days") int days) {
        return weatherDataService.getWeatherForecast(city, apiKey, days);
    }

}
