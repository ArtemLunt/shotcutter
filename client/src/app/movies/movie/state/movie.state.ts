import { DeleteLikeAction, LikeMovieAction, SetMovieLikesSummaryAction } from './movie.actions';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { IMovieLikesSummary, MoviesService } from '@sc/movies';
import { increment } from '@sc/shared/store-operators';
import { Observable, tap, throwError } from 'rxjs';
import { iif, patch } from '@ngxs/store/operators';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';

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
  ): Observable<IMovieLikesSummary> {
    const initialState = getState().likesSummary;

    // optimistic update
    setState(patch({
      likesSummary: patch({
        likedByCurrentUser: value,
        dislikedByCurrentUser: !value,
        amountOfLikes: iif(
          () => value,
          increment(),
          iif(() => initialState.likedByCurrentUser, increment(true))
        ),
        amountOfDislikes: iif(
          () => !value,
          increment(),
          iif(() => initialState.dislikedByCurrentUser, increment(true))
        )
      })
    }));

    return this._moviesService.likeMovie(relatedMovieId, value)
      .pipe(
        tap(summary => setState(patch({ likesSummary: patch(summary) }))),
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
  ): Observable<IMovieLikesSummary> {
    const initialState = getState().likesSummary;

    // optimistic update
    setState(patch({
      likesSummary: patch({
        likedByCurrentUser: false,
        dislikedByCurrentUser: false,
        amountOfLikes: iif(initialState.likedByCurrentUser, increment(true)),
        amountOfDislikes: iif(initialState.dislikedByCurrentUser, increment(true)),
      })
    }))

    return this._moviesService.deleteLike(relatedMovieId)
      .pipe(
        tap(summary => setState(patch({ likesSummary: patch(summary) }))),
        // if something went wrong - rolling back the initial state
        catchError(err => {
          setState(patch({ likesSummary: initialState }));
          return throwError(err);
        })
      );
  }
}
