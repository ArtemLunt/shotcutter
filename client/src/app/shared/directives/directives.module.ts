import {ClickOutsideDirective} from './click-outside.directive';
import {CommonModule} from '@angular/common';
import {NgModule, Type} from '@angular/core';

const DIRECTIVES: Type<any>[] = [
  ClickOutsideDirective
];

@NgModule({
  declarations: [...DIRECTIVES],
  exports: [...DIRECTIVES],
  imports: [
    CommonModule
  ]
})
export class DirectivesModule {
}
