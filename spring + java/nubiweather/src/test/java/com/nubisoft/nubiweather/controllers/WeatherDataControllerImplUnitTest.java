package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.*;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class WeatherDataControllerImplUnitTest {
    @InjectMocks
    private WeatherDataControllerImpl weatherController;

    @Mock
    private WeatherDataService weatherDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentWeather() {
        // given
        String city = "London";

        WeatherDataTo mockResponse = new WeatherDataTo();
        Location location = new Location();
        location.setName("London");

        Current current = new Current();
        current.setTempC(BigDecimal.valueOf(12.3));
        Condition condition = new Condition();
        condition.setText("Sunny");
        current.setCondition(condition);

        mockResponse.setLocation(location);
        mockResponse.setCurrent(current);

        when(weatherDataService.getCurrentWeather(any(), any())).thenReturn(mockResponse);

        // when
        WeatherDataTo result = weatherController.getCurrentWeather(city);

        // then
        assertEquals("London", result.getLocation().getName());
        assertEquals(BigDecimal.valueOf(12.3), result.getCurrent().getTempC());
        assertEquals("Sunny", result.getCurrent().getCondition().getText());
    }

    @Test
    void testGetWeatherForecast() {
        // given
        String city = "London";
        int days = 3;

        WeatherDataTo mockResponse = new WeatherDataTo();
        Location location = new Location();
        location.setName("London");

        Forecast forecast = new Forecast();
        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDate("2025-01-12");

        Condition condition = new Condition();
        condition.setText("Cloudy");
        forecastDay.setDay(new Day());
        forecastDay.getDay().setCondition(condition);

        forecast.setForecastDays(List.of(forecastDay));
        mockResponse.setLocation(location);
        mockResponse.setForecast(forecast);

        when(weatherDataService.getWeatherForecast(any(), any(), anyInt())).thenReturn(mockResponse);

        // when
        WeatherDataTo result = weatherController.getWeatherForecast(city, days);

        // then
        assertEquals("London", result.getLocation().getName());
        assertEquals("2025-01-12", result.getForecast().getForecastDays().get(0).getDate());
        assertEquals("Cloudy", result.getForecast().getForecastDays().get(0).getDay().getCondition().getText());
    }
}
