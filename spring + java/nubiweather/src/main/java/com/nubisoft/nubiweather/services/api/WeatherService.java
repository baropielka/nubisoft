package com.nubisoft.nubiweather.services.api;

import com.nubisoft.nubiweather.models.weather.WeatherInfo;

public interface WeatherService {

    WeatherInfo getCurrentWeather(String city, String apiKey);
    WeatherInfo getWeatherForecast(String city, String apiKey, int days);
}
