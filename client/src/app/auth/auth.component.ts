import { AUTH_PROVIDERS_DATA, AuthProviderDataType } from '@sc/auth/auth-provider.constants';
import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'sc-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AuthComponent {

  readonly authProviders: AuthProviderDataType[];

  constructor() {
    this.authProviders = Object.values(AUTH_PROVIDERS_DATA);
  }

}
