import { Theme } from '@sc/theme';

export class SetThemeAction {
  static readonly type = '[Theme] Set theme';
  constructor(public theme: Theme) { }
}
