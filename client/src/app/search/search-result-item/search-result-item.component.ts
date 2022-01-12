import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { IMovie } from '@sc/movies';

@Component({
  selector: 'sc-search-result-item',
  templateUrl: './search-result-item.component.html',
  styleUrls: ['./search-result-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchResultItemComponent {

  @Input()
  readonly movie: IMovie;

  @Input()
  readonly disabledGenres?: string[];

  get subtitle(): string {
    const baseSubtitle = `${(new Date(this.movie.releaseDate)).getFullYear()}`;
    return this.movie.originalTitle === this.movie.title
      ? baseSubtitle
      : `${this.movie.originalTitle} ${baseSubtitle}`;
  }

  constructor(private readonly _router: Router) {
  }

}
