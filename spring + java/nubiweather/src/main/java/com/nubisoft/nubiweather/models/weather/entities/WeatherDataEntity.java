package com.nubisoft.nubiweather.models.weather.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WeatherDataEntity extends AbstractEntity {
    @Column
    private String name;
    @Column
    private String country;
    @Column(name = "current_temp_c")
    private BigDecimal currentTempC;
    @Column
    private BigDecimal currentWindKph;
    @Column
    private String currentWeatherCondition;

    @OneToMany(
            mappedBy = "weatherData",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ForecastDayEntity> forecastDays;
}
