package com.nubisoft.nubiweather.services;

import com.nubisoft.nubiweather.models.weather.WeatherInfo;
import com.nubisoft.nubiweather.services.api.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherInfo getCurrentWeather(String city, String apiKey) {
        String url = "http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no".formatted(apiKey, city);;
        return restTemplate.getForObject(url, WeatherInfo.class);
    }

    @Override
    public WeatherInfo getWeatherForecast(String city, String apiKey, int days) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%s&aqi=no&alerts=no".formatted(apiKey, city, days);
        WeatherInfo weatherInfo = restTemplate.getForObject(url, WeatherInfo.class);
        return weatherInfo;
    }
}
