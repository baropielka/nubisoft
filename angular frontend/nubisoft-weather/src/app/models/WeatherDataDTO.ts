import {ForecastDayDTO} from './ForecastDayDTO';

export interface WeatherDataDTO {
  id: number;
  name: string;
  country: string;
  currentTempC: number;
  currentWindKph: number;
  currentWeatherCondition: string;
  forecastDays: ForecastDayDTO[];
}
