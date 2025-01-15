import {inject, Injectable} from '@angular/core';
import {WeatherDataDTO} from '../models/WeatherDataDTO';
import {BACKEND_WEATHER_URL} from '../constatnts/AppConstants';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material/dialog';
import {CityNotFoundModalComponent} from '../modal/city-not-found-modal/city-not-found-modal.component';
import {catchError, EMPTY} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(
    private readonly http: HttpClient,
    private readonly dialog: MatDialog) {
  }

  public getWeather(city: string) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/current/` + city).pipe(
      catchError(() => {
        this.dialog.open(CityNotFoundModalComponent, {
          data: {cityName: city}
        });
        return EMPTY;
      }));
  }

  public getForecast(city: string, days: number) {
    return this.http.get<WeatherDataDTO>(`${BACKEND_WEATHER_URL}/forecast/${city}/${days}`).pipe(
      catchError(() => {
        this.dialog.open(CityNotFoundModalComponent, {
          data: {cityName: city}
        });
        return EMPTY;
      }));
  }

  public getLastSearched() {
    return this.http.get<WeatherDataDTO[]>(`${BACKEND_WEATHER_URL}/getLastSearches`);
  }
}
