import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {WeatherDataDTO} from '../models/WeatherDataDTO';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {of} from 'rxjs';

@Component({
  selector: 'app-weather-panel',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './weather-panel.component.html',
  styleUrl: './weather-panel.component.scss'
})
export class WeatherPanelComponent implements OnInit {

  @Input()
  weatherData!: WeatherDataDTO;
  forecastMode!: boolean;

  constructor(private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    if (!this.weatherData) {
      this.loadWeatherData();
    }
  }

  private loadWeatherData() {
    this.route.data.subscribe(data => {
      const current = data['current'];
      const forecast = data['forecast'];
      if (current) {
        this.weatherData = current;
        this.forecastMode = false;
      } else {
        this.weatherData = forecast;
        this.forecastMode = true;
      }
    })
  }
}
