package com.nubisoft.nubiweather.services;

import com.nubisoft.nubiweather.models.weather.WeatherInfo;
import com.nubisoft.nubiweather.models.weather.subelements.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class WeatherServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrentWeather_ReturnsWeatherInfo() {
        // given
        String city = "London";
        String apiKey = "test-api-key";

        WeatherInfo mockResponse = new WeatherInfo();
        Location location = new Location();
        location.setName("London");

        Current current = new Current();
        current.setTempC(BigDecimal.valueOf(5.5));
        Condition condition = new Condition();
        condition.setText("Mist");
        current.setCondition(condition);

        mockResponse.setLocation(location);
        mockResponse.setCurrent(current);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockResponse);

        // when
        WeatherInfo result = weatherService.getCurrentWeather(city, apiKey);

        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat("London").isEqualTo(result.getLocation().getName());
        softly.assertThat(BigDecimal.valueOf(5.5)).isEqualTo(result.getCurrent().getTempC());
        softly.assertThat("Mist").isEqualTo(result.getCurrent().getCondition().getText());
        softly.assertAll();
    }

    @Test
    void getWeatherForecast_ReturnsWeatherInfo() {
        // given
        String city = "London";
        String apiKey = "test-api-key";
        int days = 3;

        WeatherInfo mockResponse = new WeatherInfo();
        Location location = new Location();
        location.setName("London");

        Forecast forecast = new Forecast();
        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDate("2025-01-12");

        Day day = new Day();
        day.setMaxTempC(10.5);
        day.setMinTempC(3.2);
        Condition condition = new Condition();
        condition.setText("Sunny");
        condition.setCode(1000);
        day.setCondition(condition);

        forecastDay.setDay(day);
        forecast.setForecastDays(List.of(forecastDay));

        mockResponse.setLocation(location);
        mockResponse.setForecast(forecast);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockResponse);

        // when
        WeatherInfo result = weatherService.getWeatherForecast(city, apiKey, days);

        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat("London").isEqualTo(result.getLocation().getName());
        softly.assertThat(10.5).isEqualTo(result.getForecast().getForecastDays().get(0).getDay().getMaxTempC());
        softly.assertThat("Sunny").isEqualTo(result.getForecast().getForecastDays().get(0).getDay().getCondition().getText());
        softly.assertAll();
    }
}