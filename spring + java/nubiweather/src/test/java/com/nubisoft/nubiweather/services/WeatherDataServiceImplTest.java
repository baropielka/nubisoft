package com.nubisoft.nubiweather.services;

import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.Condition;
import com.nubisoft.nubiweather.models.weather.to.subelements.Current;
import com.nubisoft.nubiweather.models.weather.to.subelements.Location;
import com.nubisoft.nubiweather.repositories.ForecastDayRepository;
import com.nubisoft.nubiweather.repositories.WeatherDataRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WeatherDataServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private ForecastDayRepository forecastDayRepository;

    @InjectMocks
    private WeatherDataServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrentWeather_ReturnsWeatherInfo() {
        // given
        String city = "London";
        String apiKey = "test-api-key";
        WeatherDataTo mockResponse = createMockWeatherData();

        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockResponse);

        // when
        WeatherDataTo result = weatherService.getCurrentWeather(city, apiKey);

        // Assert
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getLocation().getName()).isEqualTo("London");
        softly.assertThat(result.getCurrent().getTempC()).isEqualTo(BigDecimal.valueOf(5.5));
        softly.assertThat(result.getCurrent().getCondition().getText()).isEqualTo("Mist");
        softly.assertAll();
        verify(weatherDataRepository, times(1)).count();
    }

    @Test
    void getCurrentWeather_ThrowsException_WhenCityIsNull() {
        // given
        String city = null;
        String apiKey = "test-api-key";

        // when & then
        assertThrows(NullPointerException.class, () -> weatherService.getCurrentWeather(city, apiKey));
    }

    @Test
    void getCurrentWeather_ThrowsException_WhenApiKeyIsNull() {
        // given
        String city = "London";
        String apiKey = null;

        // when & then
        assertThrows(NullPointerException.class, () -> weatherService.getCurrentWeather(city, apiKey));
    }

    @Test
    void getWeatherForecast_ThrowsException_WhenCityIsNull() {
        // given
        String city = null;
        String apiKey = "test-api-key";
        int days = 3;

        // when & then
        assertThrows(NullPointerException.class, () -> weatherService.getWeatherForecast(city, apiKey, days));
    }

    @Test
    void getWeatherForecast_ThrowsException_WhenApiKeyIsNull() {
        // given
        String city = "London";
        String apiKey = null;
        int days = 3;

        // when & then
        assertThrows(NullPointerException.class, () -> weatherService.getWeatherForecast(city, apiKey, days));
    }

    private WeatherDataTo createMockWeatherData() {
        WeatherDataTo mockResponse = new WeatherDataTo();
        Location location = new Location();
        location.setName("London");

        Current current = new Current();
        current.setTempC(BigDecimal.valueOf(5.5));
        Condition condition = new Condition();
        condition.setText("Mist");
        current.setCondition(condition);

        mockResponse.setLocation(location);
        mockResponse.setCurrent(current);
        return mockResponse;
    }
}