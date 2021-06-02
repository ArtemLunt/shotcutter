import {SetThemeAction, Theme, THEME_ICONS, ThemeState} from '@sc/theme';
import {Component, ChangeDetectionStrategy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, map} from 'rxjs/operators';
import {Store} from '@ngxs/store';
import {Observable} from 'rxjs';

@Component({
    selector: 'sc-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent implements OnInit {

    showDetailsMenu: boolean;
    readonly themeIcon$: Observable<string>;

    constructor(
        private readonly _store: Store,
        private _router: Router
    ) {
        this.showDetailsMenu = false;
        this.themeIcon$ = _store.select(ThemeState.currentTheme)
            .pipe(map(theme => THEME_ICONS[theme]));
    }

    ngOnInit(): void {
      this._router.events
          .pipe(filter(evt => evt instanceof NavigationStart))
          .subscribe(() => this.showDetailsMenu = false)
          .registerFor(this);
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
