package com.nubisoft.nubiweather.models.weather.to;

import com.nubisoft.nubiweather.models.weather.to.subelements.Current;
import com.nubisoft.nubiweather.models.weather.to.subelements.Forecast;
import com.nubisoft.nubiweather.models.weather.to.subelements.Location;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherDataTo {
    //Note to reviewer: I made this weird looking DTO to match the JSON coming from the WeatherAPI
    private Location location;
    private Current current;
    private Forecast forecast;
}
