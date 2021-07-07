import { CleanSearchResultsAction, SearchAction } from '@sc/search/state/search.actions';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { SearchService } from '@sc/search/search.service';
import { append, patch } from '@ngxs/store/operators';
import { IPage } from '@sc/shared/interfaces';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { IMovie } from '@sc/movies';
import { Observable } from 'rxjs';

export class SearchStateModel {
  public searchResults: IMovie[];
  public currentPage: IPage<IMovie>;
  public totalPages: number;
  public searchInProgress: boolean;
}

const defaults: SearchStateModel = {
  searchResults: null,
  totalPages: null,
  currentPage: null,
  searchInProgress: false
};

@State<SearchStateModel>({
  name: 'search',
  defaults
})
@Injectable()
export class SearchState {

  @Selector()
  static searchResults({searchResults}: SearchStateModel): IMovie[] {
    return searchResults;
  }

  @Selector()
  static currentPage({currentPage}: SearchStateModel): IPage<IMovie> {
    return currentPage;
  }

  @Selector()
  static searchInProgress({searchInProgress}: SearchStateModel): boolean {
    return searchInProgress;
  }

  constructor(private readonly _searchService: SearchService) {
  }

  @Action(SearchAction)
  search(
    {getState, setState}: StateContext<SearchStateModel>,
    {searchParams}: SearchAction
  ): Observable<IPage<IMovie>> {
    const {currentPage} = getState();
    setState(patch({searchInProgress: true}));

    return this._searchService.searchMovies(
      searchParams,
      (currentPage?.pageable?.pageNumber ?? -1) + 1
    ).pipe(
      tap(page => {
        setState(patch({
          searchResults: append(page.content),
          currentPage: page,
          searchInProgress: false
        }))
      })
    )
  }

  @Action(CleanSearchResultsAction)
  cleanSearchResults(
    {setState}: StateContext<SearchStateModel>,
  ): void {
    setState(defaults)
  }

}
