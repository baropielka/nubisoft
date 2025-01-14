import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from '@angular/router';
import {noop} from 'rxjs';

@Component({
  selector: 'app-home',
    imports: [
        ReactiveFormsModule
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  title = 'nubisoft-weather';
  weatherChoiceFormGroup!: FormGroup;
  isWeatherButtonActive = false;
  isForecastButtonActive = false;

  constructor(
    private fb: FormBuilder,
    private router: Router) {
  }

  ngOnInit(): void {
    this.initForms();
    this.registerButtonsListener();
  }

  private initForms() {
    this.weatherChoiceFormGroup = this.fb.group({
      currentCity: '',
      forecastCity: ''
    });
  }

  getWeather(city?: string) {
    if (city) {
      this.router.navigate(['/weather/current/', city]).then(noop);
    } else {
      const cityFromForm = this.weatherChoiceFormGroup.get('currentCity')?.value;
      this.router.navigate(['/weather/current/', cityFromForm]).then(noop);

    }
  }

  getForecast(city?: string) {
    if (city) {
      this.router.navigate(['/weather/forecast/', city]).then(noop);
    } else {
      const cityFromForm = this.weatherChoiceFormGroup.get('forecastCity')?.value;
      this.router.navigate(['/weather/current/', cityFromForm]).then(noop);

    }
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
}
