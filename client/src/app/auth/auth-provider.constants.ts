import { environment } from '@sc/environments/environment';
import { HttpParam } from '@sc/shared/enums';

export type AuthProviderKey = 'google' | 'yandex';

export type AuthProviderDataType = {
  title: string;
  icon: string;
  url: string;
};

export const AUTH_PROVIDERS_DATA: Record<AuthProviderKey, AuthProviderDataType> = {
  google: {
    title: 'Google',
    icon: '/assets/images/auth-providers/google.png',
    get url(): string {
      return `${environment.ssoUrl}/oauth2/authorization/google?${HttpParam.RedirectTo}=${window.location.href}`;
    }
  },
  yandex: {
    title: 'Yandex',
    icon: '/assets/images/auth-providers/yandex.png',
    get url(): string {
      return `${environment.ssoUrl}/oauth2/authorization/yandex?${HttpParam.RedirectTo}=${window.location.href}`;
    }
  }
};
