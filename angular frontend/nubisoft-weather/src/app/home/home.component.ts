import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router} from '@angular/router';
import {WeatherDataDTO} from '../models/WeatherDataDTO';
import {WeatherPanelComponent} from '../weather-panel/weather-panel.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-home',
  imports: [
    ReactiveFormsModule,
    WeatherPanelComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  title = 'nubisoft-weather';
  weatherChoiceFormGroup!: FormGroup;
  isWeatherButtonActive = false;
  isForecastButtonActive = false;
  lastSearches!: WeatherDataDTO[];

  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.initForms();
    this.registerButtonsListener();
    this.loadData();
  }

  getWeather(city?: string) {
    if (city) {
      city = this.replaceSpecialChars(city)
      this.router.navigate(['/weather/current/', city]);
    } else {
      const cityFromForm = this.replaceSpecialChars(this.weatherChoiceFormGroup.get('currentCity')?.value);
      this.router.navigate(['/weather/current/', cityFromForm]);

    }
  }

  getForecast(city?: string) {
    if (city) {
      city = this.replaceSpecialChars(city)
      this.router.navigate(['/weather/forecast/', city]);
    } else {
      const cityFromForm = this.replaceSpecialChars(this.weatherChoiceFormGroup.get('forecastCity')?.value);
      this.router.navigate(['/weather/forecast/', cityFromForm]);

    }
  }

  private initForms() {
    this.weatherChoiceFormGroup = this.fb.group({
      currentCity: '',
      forecastCity: ''
    });
  }

  private registerButtonsListener() {
    this.weatherChoiceFormGroup.get('currentCity')?.valueChanges.subscribe((value: string) => {
      this.isWeatherButtonActive = !!value.trim();
      this.weatherChoiceFormGroup.get('forecastCity')?.patchValue('', {emitEvent: false});
      this.isForecastButtonActive = false;
    });

    this.weatherChoiceFormGroup.get('forecastCity')?.valueChanges.subscribe((value: string) => {
      this.isForecastButtonActive = !!value.trim();
      this.weatherChoiceFormGroup.get('currentCity')?.patchValue('', {emitEvent: false});
      this.isWeatherButtonActive = false;
    });
  }

  private loadData() {
    this.route.data.subscribe(data => {
      this.lastSearches = data['lastSearches'];
    })
  }

  private replaceSpecialChars(str: string): string {
    const specialCharsMap: Record<string, string> = {
      'ą': 'a',
      'ć': 'c',
      'ę': 'e',
      'ł': 'l',
      'ń': 'n',
      'ó': 'o',
      'ś': 's',
      'ź': 'z',
      'ż': 'z',
    };

    return str.replace(/[ąćęłńóśźżäöüß]/g, (char) => specialCharsMap[char] || char);
  }
}
