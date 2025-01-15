package com.nubisoft.nubiweather.mappers;

import com.nubisoft.nubiweather.models.weather.entities.ForecastDayEntity;
import com.nubisoft.nubiweather.models.weather.entities.WeatherDataEntity;
import com.nubisoft.nubiweather.models.weather.to.WeatherDataTo;
import com.nubisoft.nubiweather.models.weather.to.subelements.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
        weatherData.setCurrentWeatherIconLink(current.getCondition().getIcon());
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

    public static WeatherDataTo mapToWeatherDataTo(WeatherDataEntity weatherEntity) {
        if (weatherEntity == null) {
            return null;
        }

        WeatherDataTo weatherDataTo = new WeatherDataTo();
        weatherDataTo.setLocation(mapToLocation(weatherEntity));
        weatherDataTo.setCurrent(mapToCurrent(weatherEntity));
        weatherDataTo.setForecast(mapToForecast(weatherEntity));

        return weatherDataTo;
    }

    private static Location mapToLocation(WeatherDataEntity weatherEntity) {
        if (weatherEntity == null) {
            return null;
        }

        Location location = new Location();
        location.setName(weatherEntity.getName());
        location.setCountry(weatherEntity.getCountry());
        return location;
    }

    private static Current mapToCurrent(WeatherDataEntity weatherEntity) {
        if (weatherEntity == null) {
            return null;
        }

        Current current = new Current();
        current.setTempC(weatherEntity.getCurrentTempC());
        current.setCondition(mapToCondition(weatherEntity));
        current.setWindKph(weatherEntity.getCurrentWindKph());
        return current;
    }

    private static Condition mapToCondition(WeatherDataEntity weatherEntity) {
        if (weatherEntity == null) {
            return null;
        }

        Condition condition = new Condition();
        condition.setText(weatherEntity.getCurrentWeatherCondition());
        condition.setIcon(weatherEntity.getCurrentWeatherIconLink());
        return condition;
    }

    private static Forecast mapToForecast(WeatherDataEntity weatherEntity) {
        if (weatherEntity == null || weatherEntity.getForecastDays() == null) {
            return null;
        }

        Forecast forecast = new Forecast();
        List<ForecastDay> forecastDays = weatherEntity.getForecastDays()
                .stream()
                .map(WeatherDataMapper::mapToForecastDay)
                .collect(Collectors.toList());
        forecast.setForecastDays(forecastDays);

        return forecast;
    }

    private static ForecastDay mapToForecastDay(ForecastDayEntity forecastDayEntity) {
        if (forecastDayEntity == null) {
            return null;
        }

        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDate(forecastDayEntity.getDate().toString());
        forecastDay.setDay(mapToDay(forecastDayEntity));
        return forecastDay;
    }

    private static Day mapToDay(ForecastDayEntity forecastDayEntity) {
        if (forecastDayEntity == null) {
            return null;
        }

        Day day = new Day();
        Condition condition = new Condition();
        day.setCondition(condition);
        day.setMaxTempC(forecastDayEntity.getMaxTempC());
        day.setMinTempC(forecastDayEntity.getMinTempC());
        day.getCondition().setText(forecastDayEntity.getWeatherCondition());
        return day;
    }
}
