import { State, Action, StateContext, NgxsOnInit, Selector } from '@ngxs/store';
import { LoadGenresAction } from '@sc/genres/state/genres.actions';
import { GenresService } from '@sc/genres/genres.service';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

export class GenresStateModel {
  genres: string[];
}

@State<GenresStateModel>({
  name: 'genres',
  defaults: {
    genres: null
  }
})
@Injectable()
export class GenresState implements NgxsOnInit {

  @Selector()
  static genres({genres}: GenresStateModel): string[] {
    return genres;
  }

  constructor(
    private readonly _genresService: GenresService
  ) {
  }

  ngxsOnInit(ctx?: StateContext<any>): any {
    ctx.dispatch(new LoadGenresAction());
  }

  @Action(LoadGenresAction)
  loadGenres({patchState}: StateContext<GenresStateModel>): Observable<string[]> {
    return this._genresService.getGenres()
      .pipe(
        tap(genres => patchState({genres}))
      );
  }

}
