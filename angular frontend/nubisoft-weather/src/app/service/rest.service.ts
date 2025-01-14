import {Injectable} from '@angular/core';
import {WeatherDataDTO} from '../models/WeatherDataDTO';
import {BACKEND_WEATHER_URL} from '../constatnts/AppConstants';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private readonly http: HttpClient) {
  }

  public getWeather(city: string) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/current/` + city);
  }

  public getForecast(city: string, days: number) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/forecast/${city}/${days}`);
  }
}
