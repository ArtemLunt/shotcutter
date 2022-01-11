import { CleanSearchResultsAction, SearchAction } from '@sc/search/state/search.actions';
import { Component, ChangeDetectionStrategy, OnInit, OnDestroy } from '@angular/core';
import { TypedFormBuilder, TypedFormGroup } from '@sc/shared/typed-forms';
import { SearchQueryParam } from '@sc/search/search-query-param.enum';
import { ISearchParams } from '@sc/search/search-params.interface';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Comparators, omitNullishFields } from '@sc/shared/utils';
import { SearchState } from '@sc/search/state/search.state';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { IPage } from '@sc/shared/interfaces';
import { Select, Store } from '@ngxs/store';
import { GenresState } from '@sc/genres';
import { IMovie } from '@sc/movies';
import { Observable } from 'rxjs';

@Component({
  selector: 'sc-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit, OnDestroy {

  @Select(SearchState.searchResults)
  readonly results$: Observable<IMovie[]>;
  @Select(SearchState.currentPage)
  readonly currentPage$: Observable<IPage<IMovie>>;
  @Select(SearchState.searchInProgress)
  readonly searchInProgress$: Observable<boolean>;
  @Select(GenresState.genres)
  readonly genres$: Observable<string[]>;

  readonly searchParams$: Observable<ISearchParams>;

  readonly searchConfigForm: TypedFormGroup<any>;

  constructor(
    private readonly _router: Router,
    private readonly _store: Store,
    activatedRoute: ActivatedRoute,
    formBuilder: TypedFormBuilder,
  ) {
    this.searchConfigForm = formBuilder.group<ISearchParams>({
      query: [null],
      genres: [null],
    });

    this.searchParams$ = activatedRoute.queryParamMap
      .pipe(
        map(this._extractSearchParams.bind(this)),
        distinctUntilChanged(Comparators.deepComparePr),
      );
  }

  ngOnInit() {
    this.searchParams$
      .subscribe(params => {
        this._store.dispatch(new SearchAction(params));
        this.searchConfigForm.patchValue(params);
      })
      .registerFor(this);
  }

  ngOnDestroy(): void {
    this._store.dispatch(new CleanSearchResultsAction());
  }

  search(queryParams: ISearchParams): void {
    this._store.dispatch(new CleanSearchResultsAction());
    this._router.navigate(['home/search'], {
      queryParams
    });
  }

  private _extractSearchParams(paramMap: ParamMap): ISearchParams {
    return omitNullishFields({
      query: paramMap.get(SearchQueryParam.Query),
      genres: paramMap.getAll(SearchQueryParam.Genres)
    });
  }

}
