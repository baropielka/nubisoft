package com.nubisoft.nubiweather.models.weather.subelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Forecast {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastDays;
}
