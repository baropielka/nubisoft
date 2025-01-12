package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.controllers.api.WeatherController;
import com.nubisoft.nubiweather.models.weather.WeatherInfo;
import com.nubisoft.nubiweather.services.api.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherControllerImpl implements WeatherController {

    private final WeatherService weatherService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherControllerImpl(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    @GetMapping("/current/{city}")
    public WeatherInfo getCurrentWeather(@PathVariable String city) {
        return weatherService.getCurrentWeather(city, apiKey);
    }

    @Override
    @GetMapping("/forecast/{city}/{days}")
    public WeatherInfo getWeatherForecast(@PathVariable String city, @PathVariable("days") int days) {
        return weatherService.getWeatherForecast(city, apiKey, days);
    }

}
