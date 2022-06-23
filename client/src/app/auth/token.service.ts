import { CookieKey } from '@sc/auth/cookie-key.enum';
import { Injectable } from '@angular/core';
import * as Cookies from 'js-cookie';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  getToken(): string {
    return Cookies.get(CookieKey.AccessToken);
  }

  hasToken(): boolean {
    return !!this.getToken();
  }

  setToken(token: string): void {
    Cookies.set(CookieKey.AccessToken, token);
  }

  removeToken(): void {
    Cookies.remove(CookieKey.AccessToken);
  }

}
