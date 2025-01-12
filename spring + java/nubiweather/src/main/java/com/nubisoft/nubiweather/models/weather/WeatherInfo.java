package com.nubisoft.nubiweather.models.weather;

import com.nubisoft.nubiweather.models.weather.subelements.Current;
import com.nubisoft.nubiweather.models.weather.subelements.Forecast;
import com.nubisoft.nubiweather.models.weather.subelements.Location;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherInfo {
    private Location location;
    private Current current;
    private Forecast forecast;
}
