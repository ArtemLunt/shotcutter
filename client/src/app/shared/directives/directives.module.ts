import { StopPropagationDirective } from '@sc/shared/directives/stop-propagation.directive';
import { ClickOutsideDirective } from '@sc/shared/directives/click-outside.directive';
import { CommonModule } from '@angular/common';
import { NgModule, Type } from '@angular/core';

const DIRECTIVES: Type<any>[] = [
  StopPropagationDirective,
  ClickOutsideDirective,
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
