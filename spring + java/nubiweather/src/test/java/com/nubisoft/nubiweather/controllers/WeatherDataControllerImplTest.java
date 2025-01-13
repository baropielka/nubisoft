package com.nubisoft.nubiweather.controllers;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.Condition;
import com.nubisoft.nubiweather.models.weather.to.subelements.Current;
import com.nubisoft.nubiweather.models.weather.to.subelements.Location;
import com.nubisoft.nubiweather.services.api.WeatherDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@WebMvcTest(WeatherDataControllerImpl.class)
public class WeatherDataControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherDataService weatherDataService;

    @Value("${weather.api.key}")
    private String apiKey = "testApiKey"; // Provide a mock value

    private WeatherDataTo mockWeatherData;

    @BeforeEach
    public void setUp() {
        // Prepare mock WeatherDataTo object using no-args constructors and setters
        mockWeatherData = new WeatherDataTo();

        Location location = new Location();
        location.setName("London");
        location.setCountry("UK");

        Current current = new Current();
        current.setTempC(new BigDecimal("15.0"));
        current.setWindKph(new BigDecimal("10.5"));
        Condition condition = new Condition();
        condition.setText("Sunny");
        current.setCondition(condition);

        mockWeatherData.setLocation(location);
        mockWeatherData.setCurrent(current);
        mockWeatherData.setForecast(null); // No forecast in this test
    }

    @Test
    public void getCurrentWeather_ShouldReturnWeatherDataTo_WhenCityIsValid() throws Exception {
        String city = "London";

        Mockito.when(weatherDataService.getCurrentWeather(city, apiKey)).thenReturn(mockWeatherData);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/current/{city}", city))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.country").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.wind_kph").value("10.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.condition.text").value("Sunny"));

        Mockito.verify(weatherDataService, Mockito.times(1)).getCurrentWeather(city, apiKey);
    }

    @Test
    public void getWeatherForecast_ShouldReturnWeatherDataTo_WhenCityAndDaysAreValid() throws Exception {
        String city = "London";
        int days = 3;

        Mockito.when(weatherDataService.getWeatherForecast(city, apiKey, days)).thenReturn(mockWeatherData);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/forecast/{city}/{days}", city, days))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.country").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.wind_kph").value("10.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.condition.text").value("Sunny"));

        Mockito.verify(weatherDataService, Mockito.times(1)).getWeatherForecast(city, apiKey, days);
    }
}