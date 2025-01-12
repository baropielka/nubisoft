package com.nubisoft.nubiweather.models.weather.subelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Day {
    @JsonProperty("maxtemp_c")
    private double maxTempC;
    @JsonProperty("mintemp_c")
    private double minTempC;
    @JsonProperty("avgtemp_c")
    private double avgTempC;
    @JsonProperty("daily_will_it_rain")
    private int dailyWillItRain;
    @JsonProperty("daily_chance_of_rain")
    private int dailyChanceOfRain;
    @JsonProperty("daily_will_it_snow")
    private int dailyWillItSnow;
    @JsonProperty("daily_chance_of_snow")
    private int dailyChanceOfSnow;
    private Condition condition;
}
