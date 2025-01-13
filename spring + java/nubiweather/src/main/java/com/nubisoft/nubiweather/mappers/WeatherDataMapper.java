package com.nubisoft.nubiweather.mappers;

import com.nubisoft.nubiweather.models.weather.entities.ForecastDayEntity;
import com.nubisoft.nubiweather.models.weather.entities.WeatherDataEntity;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.Current;
import com.nubisoft.nubiweather.models.weather.to.subelements.Day;
import com.nubisoft.nubiweather.models.weather.to.subelements.ForecastDay;
import com.nubisoft.nubiweather.models.weather.to.subelements.Location;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherDataMapper {
    public static WeatherDataEntity mapToEntity(WeatherDataTo weatherDataTo) {
        WeatherDataEntity weatherData = new WeatherDataEntity();

        Location location = weatherDataTo.getLocation();
        weatherData.setName(location.getName());
        weatherData.setCountry(location.getCountry());

        Current current = weatherDataTo.getCurrent();
        weatherData.setCurrentTempC(current.getTempC());
        weatherData.setCurrentWindKph(current.getWindKph());

        weatherData.setCurrentWeatherCondition(current.getCondition().getText());
        if (weatherDataTo.getForecast() != null) {
            weatherData.setForecastDays(mapForecastDays(weatherDataTo.getForecast().getForecastDays(), weatherData));
        }
        return weatherData;
    }

    private static List<ForecastDayEntity> mapForecastDays(List<ForecastDay> forecastDays, WeatherDataEntity weatherDataEntity) {
        return forecastDays
                .stream()
                .map(forecastDay -> mapForecastDayEntity(forecastDay, weatherDataEntity))
                .toList();
    }

    private static ForecastDayEntity mapForecastDayEntity(ForecastDay forecastDay, WeatherDataEntity weatherDataEntity) {
        ForecastDayEntity entity = new ForecastDayEntity();
        entity.setDate(convertToDate(forecastDay.getDate()));
        Day day = forecastDay.getDay();
        entity.setMaxTempC(day.getMaxTempC());
        entity.setMinTempC(day.getMinTempC());
        entity.setAvgTempC(day.getAvgTempC());
        entity.setWeatherCondition(day.getCondition().getText());
        entity.setWeatherData(weatherDataEntity);
        return entity;
    }

    public static LocalDate convertToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(dateString, formatter);
    }
}
