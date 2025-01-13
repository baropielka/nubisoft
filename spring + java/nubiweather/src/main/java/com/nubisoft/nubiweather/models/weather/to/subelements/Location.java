package com.nubisoft.nubiweather.models.weather.to.subelements;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private String name;
    private String country;
}
