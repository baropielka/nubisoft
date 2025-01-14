import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WeatherDataDTO} from '../models/WeatherDataDTO';
import {BACKEND_WEATHER_URL} from '../constatnts/AppConstants';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) {
  }

  public getWeather(city: string) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/current/` + city);
  }

  public getForecast(city: string, days: number) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/forecast/${city}/${days}`);
  }
}
