package com.nubisoft.nubiweather.controllers.api;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;

public interface WeatherDataController {


    WeatherDataTo getCurrentWeather(String city);

    WeatherDataTo getWeatherForecast(String city, int days);
}
