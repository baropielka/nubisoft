package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.models.weather.WeatherInfo;
import com.nubisoft.nubiweather.models.weather.subelements.*;
import com.nubisoft.nubiweather.services.api.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherControllerImpl.class)
class WeatherControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Value("${weather.api.key}")
    private String apiKey;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getCurrentWeather_ReturnsWeatherInfo() throws Exception {
        // given
        String city = "London";

        WeatherInfo mockResponse = new WeatherInfo();
        Location location = new Location();
        location.setName("London");

        Current current = new Current();
        current.setTempC(BigDecimal.valueOf(12.3));
        Condition condition = new Condition();
        condition.setText("Sunny");
        current.setCondition(condition);

        mockResponse.setLocation(location);
        mockResponse.setCurrent(current);

        when(weatherService.getCurrentWeather(city, apiKey)).thenReturn(mockResponse);

        // when and then
        mockMvc.perform(get("/weather/current/London"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.name").value("London"))
                .andExpect(jsonPath("$.current.temp_c").value(12.3))
                .andExpect(jsonPath("$.current.condition.text").value("Sunny"));
        ;
    }

    @Test
    void getWeatherForecast_ReturnsWeatherInfo() throws Exception {
        // given
        String city = "London";
        int days = 3;

        WeatherInfo mockResponse = new WeatherInfo();
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

        when(weatherService.getWeatherForecast(city, apiKey, days)).thenReturn(mockResponse);

        // when and then
        mockMvc.perform(get("/weather/forecast/{city}/{days}", city, days))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.name").value("London"))
                .andExpect(jsonPath("$.forecast.forecastday[0].date").value("2025-01-12"))
                .andExpect(jsonPath("$.forecast.forecastday[0].day.condition.text").value("Cloudy"));
        ;
    }
}