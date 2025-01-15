package com.nubisoft.nubiweather.controllers.api;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/weather")
public interface WeatherDataController {

    @GetMapping("/current/{city}")
    WeatherDataTo getCurrentWeather(@PathVariable String city);

    @GetMapping("/forecast/{city}/{days}")
    WeatherDataTo getWeatherForecast(@PathVariable String city, @PathVariable int days);

    @GetMapping("/getLastSearches")
    List<WeatherDataTo> getLastSearches();
}
