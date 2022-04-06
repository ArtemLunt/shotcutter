import { DeleteLikeAction, LikeMovieAction } from '@sc/movies/movie/state/movie.actions';
import { Component, ChangeDetectionStrategy } from '@angular/core';
import { MovieState } from '@sc/movies/movie/state/movie.state';
import { IMovie, IMovieLikesSummary } from '@sc/movies';
import { ActivatedRoute } from '@angular/router';
import { Select, Store } from '@ngxs/store';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'sc-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MovieComponent {

  @Select(MovieState.selectMovieLikesSummary)
  readonly movieLikesSummary$: Observable<IMovieLikesSummary>;
  readonly movie$: Observable<IMovie>;

  constructor(
    private readonly _store: Store,
    route: ActivatedRoute
  ) {
    this.movie$ = route.data
      .pipe(map(({ data: { movie } }) => movie));
  }

  public likeMovie(relatedMovieId: number, value: boolean): void {
    this._store.dispatch(new LikeMovieAction(relatedMovieId, value));
  }

  public deleteLike(relatedMovieId: number): void {
    this._store.dispatch(new DeleteLikeAction(relatedMovieId));
  }

}
