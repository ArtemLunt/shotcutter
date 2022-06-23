import { FileDropAreaComponent } from '@sc/shared/components/file-drop-area/file-drop-area.component';
import { MaterialModule } from '@sc/shared/material.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

@NgModule({
  declarations: [FileDropAreaComponent],
  exports: [FileDropAreaComponent],
  imports: [
    MaterialModule,
    CommonModule,
  ]
})
export class ComponentsModule { }
