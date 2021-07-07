import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatRippleModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {NgModule, Type} from '@angular/core';

const PUBLIC_MODULES: Type<any>[] = [
  MatProgressSpinnerModule,
  MatFormFieldModule,
  MatSnackBarModule,
  MatRippleModule,
  MatButtonModule,
  MatInputModule,
  MatCardModule,
  MatIconModule,
];

@NgModule({
  imports: [
    ...PUBLIC_MODULES
  ],
  exports: [
    ...PUBLIC_MODULES
  ]
})
export class MaterialModule {
}
