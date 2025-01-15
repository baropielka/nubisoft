import {Routes} from '@angular/router';
import {WeatherPanelComponent} from './weather-panel/weather-panel.component';
import {HomeComponent} from './home/home.component';
import {
  currentWeatherDataResolver,
  weatherForecastDataResolver
} from './weather-panel/resolver/current-weather-data.resolver';
import {lastSearchesResolver} from './home/last-searches.resolver';

export const routes: Routes = [
  {
    path: 'weather/current/:city',
    component: WeatherPanelComponent,
    resolve: {
      current: currentWeatherDataResolver
    }
  },
  {
    path: 'weather/forecast/:city',
    component: WeatherPanelComponent,
    resolve: {
      forecast: weatherForecastDataResolver
    }
  },
  {
    path: 'home',
    component: HomeComponent,
    resolve: {
      lastSearches: lastSearchesResolver
    }
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  },
];
