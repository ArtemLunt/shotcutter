import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {NgModule, Type} from '@angular/core';

const PUBLIC_MODULES: Type<any>[] = [
  MatCardModule,
  MatButtonModule,
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
