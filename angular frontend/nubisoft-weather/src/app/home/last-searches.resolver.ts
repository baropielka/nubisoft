import { ResolveFn } from '@angular/router';
import {inject} from '@angular/core';
import {RestService} from '../service/rest.service';
import {WeatherDataDTO} from '../models/WeatherDataDTO';

export const lastSearchesResolver: ResolveFn<WeatherDataDTO[]> = (route, state) => {
  return inject(RestService).getLastSearched();
};
