package com.nubisoft.nubiweather.repositories;

import com.nubisoft.nubiweather.models.weather.entities.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {

    @Modifying
    @Query("DELETE FROM WeatherDataEntity w WHERE w.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT MIN(w.id) FROM WeatherDataEntity w")
    Long findLowestId();
}
