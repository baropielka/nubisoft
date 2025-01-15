import com.nubisoft.nubiweather.NubiweatherApplication;
import com.nubisoft.nubiweather.controllers.WeatherDataControllerImpl;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(WeatherDataControllerImpl.class)
@ContextConfiguration(classes = NubiweatherApplication.class)
public class WeatherDataControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherDataService weatherDataService;

    @Value("${weather.api.key}")
    private String apiKey;

    private WeatherDataTo mockWeatherData;

    @BeforeEach
    public void setUp() {
        mockWeatherData = new WeatherDataTo();

        Location location = new Location();
        location.setName("London");
        location.setCountry("UK");

        Current current = new Current();
        current.setTempC(new BigDecimal("15.0"));
        current.setWindKph(new BigDecimal("12.3"));
        Condition condition = new Condition();
        condition.setText("Sunny");
        current.setCondition(condition);

        mockWeatherData.setLocation(location);
        mockWeatherData.setCurrent(current);
        mockWeatherData.setForecast(null);
    }

    @Test
    public void getCurrentWeather_ShouldReturnValidWeatherData() throws Exception {
        String city = "London";

        Mockito.when(weatherDataService.getCurrentWeather(eq(city), eq(apiKey), any())).thenReturn(mockWeatherData);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/current/{city}", city))
                .andDo(MockMvcResultHandlers.print()) // Dla debugowania
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.country").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.wind_kph").value("12.3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.condition.text").value("Sunny"));

        Mockito.verify(weatherDataService, Mockito.times(1)).getCurrentWeather(city, apiKey, "http://api.weatherapi.com/v1");
    }

    @Test
    public void getWeatherForecast_ShouldReturnValidData_ForCityAndDays() throws Exception {
        String city = "London";
        int days = 3;

        Mockito.when(weatherDataService.getWeatherForecast(eq(city), eq(apiKey), eq(days), any())).thenReturn(mockWeatherData);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/forecast/{city}/{days}", city, days))
                .andDo(MockMvcResultHandlers.print()) // Debugowanie
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.country").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.wind_kph").value("12.3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.condition.text").value("Sunny"));

        Mockito.verify(weatherDataService, Mockito.times(1)).getWeatherForecast(city, apiKey, days, "http://api.weatherapi.com/v1");
    }
}