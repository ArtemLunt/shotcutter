import { SearchResultItemComponent } from '@sc/search/search-result-item/search-result-item.component';
import { SearchRoutingModule } from '@sc/search/search-routing.module';
import { SearchComponent } from '@sc/search/search.component';
import { SearchState } from '@sc/search/state/search.state';
import { GenresModule, GenresState } from '@sc/genres';
import { PipesModule } from '@sc/shared/pipes';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@sc/shared';
import { NgxsModule } from '@ngxs/store';
import { NgModule } from '@angular/core';

@NgModule({
  declarations: [SearchComponent, SearchResultItemComponent],
  imports: [
    NgxsModule.forFeature([SearchState, GenresState]),
    SearchRoutingModule,
    CommonModule,
    GenresModule,
    SharedModule,
    PipesModule
  ]
})
export class SearchModule {
}
