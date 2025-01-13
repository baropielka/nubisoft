package com.nubisoft.nubiweather.services.api;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;

public interface WeatherDataService {

    WeatherDataTo getCurrentWeather(String city, String apiKey);

    WeatherDataTo getWeatherForecast(String city, String apiKey, int days);
}
