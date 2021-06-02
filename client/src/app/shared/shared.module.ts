import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialModule} from '@sc/shared/material.module';
import {ComponentsModule} from '@sc/shared/components';
import {DirectivesModule} from '@sc/shared/directives';
import {HttpClientModule} from '@angular/common/http';
import {NgModule, Type} from '@angular/core';
import {CommonModule} from '@angular/common';

const PUBLIC_MODULES: Type<any>[] = [
  ReactiveFormsModule,
  HttpClientModule,
  DirectivesModule,
  ComponentsModule,
  MaterialModule,
  CommonModule,
  FormsModule,
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
