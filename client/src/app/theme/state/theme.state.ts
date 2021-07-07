import {State, Action, StateContext, Selector} from '@ngxs/store';
import {SetThemeAction} from '@sc/theme/state/theme.actions';
import {Theme} from '@sc/theme/theme.enum';
import {Injectable} from '@angular/core';

export class ThemeStateModel {
  public currentTheme: Theme;
}

const defaults = {
  currentTheme: Theme.Dark
};

@State<ThemeStateModel>({
  name: 'theme',
  defaults
})
@Injectable()
export class ThemeState {

  @Selector()
  static currentTheme({currentTheme}: ThemeStateModel): Theme {
    return currentTheme;
  }

  @Action(SetThemeAction)
  setTheme(
    {setState}: StateContext<ThemeStateModel>,
    {theme: currentTheme}: SetThemeAction
  ): void {
    setState({currentTheme});
  }

}
