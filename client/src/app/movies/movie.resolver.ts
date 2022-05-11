import { SetMovieLikesSummaryAction } from '@sc/movies/movie/state/movie.actions';
import { IMovieLikesSummary } from '@sc/movies/movie-likes-summary.interface';
import { MoviesService } from '@sc/movies/movies.service';
import { IMovie } from '@sc/movies/movie.interface';
import { Observable, tap, zip } from 'rxjs';
import { Injectable } from '@angular/core';
import { Store } from '@ngxs/store';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Resolve,
} from '@angular/router';

type MoviePageData = {
  movie: IMovie,
  likesSummary: IMovieLikesSummary
};

@Injectable({
  providedIn: 'root'
})
export class MovieResolver implements Resolve<MoviePageData> {

  constructor(
    private readonly _moviesService: MoviesService,
    private readonly _store: Store
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MoviePageData> {
    return zip(
      this._moviesService.getById(route.params.id),
      this._moviesService
        .getMovieLikesSummary(route.params.id)
        .pipe(
          tap(summary => this._store.dispatch(new SetMovieLikesSummaryAction(summary)))
        ) as Observable<IMovieLikesSummary>,
      (movie, likesSummary) => ({ movie, likesSummary })
    );
  }

}
