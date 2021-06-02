import {MaterialModule} from '@sc/shared/material.module';
import {HttpClientModule} from '@angular/common/http';
import {NgModule, Type} from '@angular/core';
import {CommonModule} from '@angular/common';

const PUBLIC_MODULES: Type<any>[] = [
  HttpClientModule,
  MaterialModule,
  CommonModule,
];

@NgModule({
  declarations: [],
  imports: [
    ...PUBLIC_MODULES
  ],
  exports: [
    ...PUBLIC_MODULES
  ]
})
export class SharedModule {
}
