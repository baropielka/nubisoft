import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {WeatherPanelComponent} from './weather-panel/weather-panel.component';

@Component({
  selector: 'app-root',
  imports: [
    WeatherPanelComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'nubisoft-weather';
}
