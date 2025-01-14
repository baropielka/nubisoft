export interface WeatherDataDTO {
  location: {
    name: string;
    country: string;
  };
  current: {
    condition: {
      text: string;
      icon: string;
      code: number;
    };
    temp_c: number;
    wind_kph: number;
  };
  forecast: {
    forecastday: ForecastDayDTO[];
  };
}

export interface ForecastDayDTO {
  date: string;
  day: {
    condition: {
      text: string;
      icon: string;
      code: number;
    };
    maxtemp_c: number;
    mintemp_c: number;
    avgtemp_c: number;
  };
}
