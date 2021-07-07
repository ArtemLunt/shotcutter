import { debounceTime, distinctUntilChanged, filter, map, mergeMap, startWith, switchMap } from 'rxjs/operators';
import { SetThemeAction, Theme, THEME_ICONS, ThemeState } from '@sc/theme';
import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { IMovieLookupDTO, MoviesService } from '@sc/movies';
import {TypedFormBuilder, TypedFormControl} from '@sc/shared/typed-forms';
import { NavigationStart, Router } from '@angular/router';
import { omitNullishFields } from '@sc/shared/utils';
import { SearchQueryParam } from '@sc/search';
import { Observable, of } from 'rxjs';
import { Store } from '@ngxs/store';

@Component({
  selector: 'sc-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent implements OnInit {

  readonly lookupKeyFormControl: TypedFormControl<string>;

  readonly themeIcon$: Observable<string>;
  readonly movieLookupOptions$: Observable<IMovieLookupDTO[]>;

  showDetailsMenu: boolean;

  constructor(
    private readonly _store: Store,
    private _router: Router,
    typedFormBuilder: TypedFormBuilder,
    moviesService: MoviesService,
  ) {
    this.showDetailsMenu = false;

    this.themeIcon$ = _store.select(ThemeState.currentTheme)
      .pipe(map(theme => THEME_ICONS[theme]));

    this.lookupKeyFormControl = typedFormBuilder.control<string>('');
    this.movieLookupOptions$ = this.lookupKeyFormControl.valueChanges
      .pipe(
        filter(value => typeof value === 'string'),
        debounceTime(200),
        map(key => key?.trim()),
        distinctUntilChanged(),
        switchMap(key => !key ? of([]) : moviesService.lookup(key)),
        startWith([])
      );
  }

  ngOnInit(): void {
    this._router.events
      .pipe(filter(evt => evt instanceof NavigationStart))
      .subscribe(() => {
        this.showDetailsMenu = false;
        this.lookupKeyFormControl.reset();
      })
      .registerFor(this);

    this.lookupKeyFormControl.valueChanges
      .subscribe(value => {
        if (typeof value === 'string' || value === null) {
          return;
        }

        this.openMoviePage(value);
        this.lookupKeyFormControl.setValue('');
      })
      .registerFor(this);
  }

  openMoviePage({id}: IMovieLookupDTO): void {
    this._router.navigateByUrl(`/home/movie/${id}`);
  }

  searchMovie(): void {
    const queryParams = omitNullishFields({
      [SearchQueryParam.Query]: this.lookupKeyFormControl.value
    });

    this._router.navigate([`/home/search`], {queryParams});
  }

  switchTheme(): void {
    this._store.dispatch(
      new SetThemeAction(
        this._store.selectSnapshot(ThemeState.currentTheme) === Theme.Light
          ? Theme.Dark
          : Theme.Light
      )
    );
  }

}
