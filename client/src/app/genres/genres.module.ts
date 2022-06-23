import { GenresListComponent } from '@sc/genres/genres-list/genres-list.component';
import { RouterModule } from '@angular/router';
import { SearchQueryParam } from '@sc/search';
import { SharedModule } from '@sc/shared';
import { NgModule } from '@angular/core';

const DECLARATIONS = [
  GenresListComponent
];

@NgModule({
  declarations: [...DECLARATIONS],
  exports: [...DECLARATIONS],
  imports: [
    SharedModule,
    RouterModule
  ],
  providers: [
    {
      provide: SearchQueryParam,
      useValue: SearchQueryParam
    }
  ]
})
export class GenresModule {
}
