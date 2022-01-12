import { Component, ChangeDetectionStrategy, Input } from '@angular/core';
import { SearchQueryParam } from '@sc/search';

@Component({
  selector: 'sc-genres-list',
  templateUrl: './genres-list.component.html',
  styleUrls: ['./genres-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GenresListComponent {

  @Input()
  readonly genresList: string[];

  @Input()
  readonly disabledGenres?: string[];

  isSearchDisabledForGenre(genre: string): boolean {
    return this.disabledGenres?.includes(genre);
  }

  getSearchQueryParams(genre: string): any {
    return {
      [SearchQueryParam.Genres]: genre
    };
  }

}
