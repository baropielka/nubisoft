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
public class Location {
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    @JsonProperty("tz_id")
    private String tzId;
    @JsonProperty("localtime_epoch")
    private long localtimeEpoch;
    private String localtime;
}
