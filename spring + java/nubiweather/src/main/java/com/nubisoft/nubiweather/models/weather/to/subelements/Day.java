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
public class Day {
    @JsonProperty("maxtemp_c")
    private BigDecimal maxTempC;
    @JsonProperty("mintemp_c")
    private BigDecimal minTempC;
    @JsonProperty("avgtemp_c")
    private BigDecimal avgTempC;
    private Condition condition;
}
