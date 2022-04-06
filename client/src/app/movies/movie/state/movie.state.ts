import { State, Action, StateContext, Selector } from '@ngxs/store';
import { DeleteLikeAction, LikeMovieAction, SetMovieLikesSummaryAction } from './movie.actions';
import { IMovieLikesSummary, MoviesService } from '@sc/movies';
import { Injectable } from '@angular/core';
import { Observable, tap, throwError } from 'rxjs';
import { IMovieLike } from '@sc/movies/movie-like.interface';
import { patch } from '@ngxs/store/operators';
import { catchError } from 'rxjs/operators';

export class MovieStateModel {
  public likesSummary?: IMovieLikesSummary
}

const defaults = {};

@State<MovieStateModel>({
  name: 'movie',
  defaults
})
@Injectable()
export class MovieState {

  @Selector()
  static selectMovieLikesSummary({ likesSummary }: MovieStateModel): IMovieLikesSummary {
    return likesSummary;
  }

  constructor(private readonly _moviesService: MoviesService) {
  }

  @Action(SetMovieLikesSummaryAction)
  setMovieLikesSummary(
    { patchState }: StateContext<MovieStateModel>,
    { likesSummary }: SetMovieLikesSummaryAction
  ): void {
    patchState({ likesSummary });
  }

  @Action(LikeMovieAction)
  likeMovieAction(
    { getState, setState }: StateContext<MovieStateModel>,
    { relatedMovieId, value }: LikeMovieAction
  ): Observable<IMovieLike> {
    const initialState = getState().likesSummary;

    // optimistic update
    setState(patch({
      likesSummary: patch({
        likedByCurrentUser: value === true,
        dislikedByCurrentUser: value === false
      })
    }))

    return this._moviesService.likeMovie(relatedMovieId, value)
      .pipe(
        // if something went wrong - rolling back the initial state
        catchError(err => {
          setState(patch({ likesSummary: initialState }));
          return throwError(err);
        })
      )
  }

  @Action(DeleteLikeAction)
  deleteLikeAction(
    { getState, setState }: StateContext<MovieStateModel>,
    { relatedMovieId }: DeleteLikeAction
  ): Observable<void> {
    const initialState = getState().likesSummary;

    // optimistic update
    setState(patch({
      likesSummary: patch({
        likedByCurrentUser: false,
        dislikedByCurrentUser: false
      })
    }))

    return this._moviesService.deleteLike(relatedMovieId)
      .pipe(
        // if something went wrong - rolling back the initial state
        catchError(err => {
          setState(patch({ likesSummary: initialState }));
          return throwError(err);
        })
      );
  }
}
