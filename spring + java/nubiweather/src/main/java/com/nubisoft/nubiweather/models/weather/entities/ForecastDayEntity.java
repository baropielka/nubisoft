package com.nubisoft.nubiweather.models.weather.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForecastDayEntity extends AbstractEntity {
    @Column
    private LocalDate date;
    @Column(name = "max_temp_c")
    private BigDecimal maxTempC;
    @Column(name = "min_temp_c")
    private BigDecimal minTempC;
    @Column(name = "avg_temp_c")
    private BigDecimal avgTempC;
    @Column
    private String weatherCondition;

    @ManyToOne
    @JoinColumn(
            name = "weather_data_id",
            nullable = false
    )
    private WeatherDataEntity weatherData;
}
