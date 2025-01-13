package com.nubisoft.nubiweather.models.weather.to.subelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Current {
    @JsonProperty("temp_c")
    private BigDecimal tempC;
    private Condition condition;
    @JsonProperty("wind_kph")
    private BigDecimal windKph;
}
