import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Theme, ThemeState } from '@sc/theme';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'fc-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  @Select(ThemeState.currentTheme)
  readonly currentTheme$: Observable<Theme>;

}
