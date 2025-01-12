package com.nubisoft.nubiweather.controllers.api;

import com.nubisoft.nubiweather.models.weather.WeatherInfo;

public interface WeatherController {


    WeatherInfo getCurrentWeather(String city);

    WeatherInfo getWeatherForecast(String city, int days);
}
