package com.nubisoft.nubiweather.repositories;

import com.nubisoft.nubiweather.models.weather.entities.ForecastDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForecastDayRepository extends JpaRepository<ForecastDayEntity, Long> {
    @Modifying
    @Query("DELETE FROM ForecastDayEntity f WHERE f.weatherData.id = :weatherDataId")
    void deleteByWeatherDataId(@Param("weatherDataId") Long weatherDataId);
}
