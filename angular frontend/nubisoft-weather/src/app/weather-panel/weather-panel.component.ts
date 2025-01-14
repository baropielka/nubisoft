import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-weather-panel',
  imports: [],
  templateUrl: './weather-panel.component.html',
  styleUrl: './weather-panel.component.scss'
})
export class WeatherPanelComponent implements OnInit {

  cityName!: string;
  forecastMode!: boolean;

  currentWeatherFormGroup!: FormGroup;
  weatherForecastFormGroupArray!: FormArray;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForms();
    this.loadWeatherData();
  }

  private initForms() {
    this.currentWeatherFormGroup = this.fb.group({
      name: '',
      country: '',
      currentTempC: '',
      currentWindKph: '',
      currentWeatherCondition: ''
    });

    this.weatherForecastFormGroupArray = this.fb.array(['date', 'maxTempC', 'minTempC', 'maxTempC', 'avgTempC', 'weatherCondition']);
    this.currentWeatherFormGroup.addControl('forecastDays', this.weatherForecastFormGroupArray);
  }

  private loadWeatherData() {

  }
}
