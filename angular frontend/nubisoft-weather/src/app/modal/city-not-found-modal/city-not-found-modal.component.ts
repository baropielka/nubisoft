import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-city-not-found-modal',
  imports: [
    MatDialogTitle,
    MatDialogActions,
    MatButton,
    MatDialogClose
  ],
  templateUrl: './city-not-found-modal.component.html',
  styleUrl: './city-not-found-modal.component.scss'
})
export class CityNotFoundModalComponent {



  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { cityName: string }
  ) {}
}
