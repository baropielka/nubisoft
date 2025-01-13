package com.nubisoft.nubiweather.mappers;

import com.nubisoft.nubiweather.models.weather.entities.ForecastDayEntity;
import com.nubisoft.nubiweather.models.weather.entities.WeatherDataEntity;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataMapperTest {

    @Test
    void mapToEntity_ShouldMapWeatherDataToEntityCorrectly() {
        // given
        Location location = new Location();
        location.setName("London");
        location.setCountry("UK");

        Condition condition = new Condition();
        condition.setText("Sunny");

        Current current = new Current();
        current.setTempC(new BigDecimal("20.5"));
        current.setWindKph(new BigDecimal("15.2"));
        current.setCondition(condition);

        ForecastDay forecastDay1 = new ForecastDay();
        forecastDay1.setDate("2025-01-12");

        Day day1 = new Day();
        day1.setMaxTempC(new BigDecimal("25.0"));
        day1.setMinTempC(new BigDecimal("15.0"));
        day1.setAvgTempC(new BigDecimal("20.0"));
        day1.setCondition(condition);
        forecastDay1.setDay(day1);

        Forecast forecast = new Forecast();
        forecast.setForecastDays(List.of(forecastDay1));

        WeatherDataTo weatherDataTo = new WeatherDataTo();
        weatherDataTo.setLocation(location);
        weatherDataTo.setCurrent(current);
        weatherDataTo.setForecast(forecast);

        // when
        WeatherDataEntity result = WeatherDataMapper.mapToEntity(weatherDataTo);

        // then
        assertNotNull(result);
        assertEquals("London", result.getName());
        assertEquals("UK", result.getCountry());
        assertTrue(new BigDecimal("20.5").compareTo(result.getCurrentTempC()) == 0);
        assertTrue(new BigDecimal("15.2").compareTo(result.getCurrentWindKph()) == 0);
        assertEquals("Sunny", result.getCurrentWeatherCondition());
        assertNotNull(result.getForecastDays());
        assertEquals(1, result.getForecastDays().size());

        ForecastDayEntity forecastDayEntity = result.getForecastDays().get(0);
        assertEquals(LocalDate.of(2025, 1, 12), forecastDayEntity.getDate());
        assertTrue(new BigDecimal("25.0").compareTo(forecastDayEntity.getMaxTempC()) == 0);
        assertTrue(new BigDecimal("15.0").compareTo(forecastDayEntity.getMinTempC()) == 0);
        assertTrue(new BigDecimal("20.0").compareTo(forecastDayEntity.getAvgTempC()) == 0);
        assertEquals("Sunny", forecastDayEntity.getWeatherCondition());
        assertEquals(result, forecastDayEntity.getWeatherData());
    }

    @Test
    void mapToEntity_ShouldHandleNullForecastGracefully() {
        // given
        Location location = new Location();
        location.setName("London");
        location.setCountry("UK");

        Condition condition = new Condition();
        condition.setText("Cloudy");

        Current current = new Current();
        current.setTempC(new BigDecimal("15.0"));
        current.setWindKph(new BigDecimal("10.0"));
        current.setCondition(condition);

        WeatherDataTo weatherDataTo = new WeatherDataTo();
        weatherDataTo.setLocation(location);
        weatherDataTo.setCurrent(current);
        weatherDataTo.setForecast(null);

        // when
        WeatherDataEntity result = WeatherDataMapper.mapToEntity(weatherDataTo);

        // then
        assertNotNull(result);
        assertEquals("London", result.getName());
        assertEquals("UK", result.getCountry());
        assertTrue(new BigDecimal("15.0").compareTo(result.getCurrentTempC()) == 0);
        assertTrue(new BigDecimal("10.0").compareTo(result.getCurrentWindKph()) == 0);
        assertEquals("Cloudy", result.getCurrentWeatherCondition());
        assertNull(result.getForecastDays());
    }

    @Test
    void convertToDate_ShouldConvertStringToLocalDate() {
        // given
        String dateString = "2025-01-12";

        // when
        LocalDate result = WeatherDataMapper.convertToDate(dateString);

        // then
        assertEquals(LocalDate.of(2025, 1, 12), result);
    }

    @Test
    void convertToDate_ShouldThrowExceptionForInvalidDateFormat() {
        // given
        String invalidDateString = "12-01-2025";

        // when & then
        assertThrows(Exception.class, () -> WeatherDataMapper.convertToDate(invalidDateString));
    }
}