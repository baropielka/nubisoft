import {ActivatedRouteSnapshot, ResolveFn, Router} from '@angular/router';
import {WeatherDataDTO} from '../../models/WeatherDataDTO';
import {inject} from '@angular/core';
import {RestService} from '../../service/rest.service';


export const currentWeatherDataResolver: ResolveFn<WeatherDataDTO> = (route: ActivatedRouteSnapshot) => {
  const queryParams = route.paramMap;
  const city = queryParams.get('city');

  if (!city) {
    inject(Router).navigate(['/home']);
    throw new Error('Missing required query parameter: city');
  }
  return inject(RestService).getWeather(city);
};
export const weatherForecastDataResolver: ResolveFn<WeatherDataDTO> = (route: ActivatedRouteSnapshot) => {
  const queryParams = route.paramMap;
  const city = queryParams.get('city');

  if (!city) {
    inject(Router).navigate(['/home']);
    throw new Error('Missing required query parameter: city');
  }
  return inject(RestService).getForecast(city, 6);
};
