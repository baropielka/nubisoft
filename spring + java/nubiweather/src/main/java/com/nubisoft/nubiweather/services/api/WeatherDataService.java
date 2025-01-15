package com.nubisoft.nubiweather.services.api;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;

import java.util.List;

public interface WeatherDataService {

    WeatherDataTo getCurrentWeather(String city, String apiKey, String apiUrl);

    WeatherDataTo getWeatherForecast(String city, String apiKey, int days, String apiUrl);

    List<WeatherDataTo> getLastSearches();
}

