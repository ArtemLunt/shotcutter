import { AuthResultQueryParamKey, AuthStatus, IAuthResult } from '@sc/auth/auth-result.interface';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthFacadeService } from '@sc/auth/auth-facade.service';
import { TokenService } from '@sc/auth/token.service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthPageGuard implements CanActivate {

  constructor(
    private readonly _authService: AuthFacadeService,
    private readonly _tokenService: TokenService,
    private readonly _router: Router
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
    return this._authService.isAuthenticated()
      .pipe(
        map(isAuthenticated => {
          // if we're already authenticated - restrict access to the auth page
          if (isAuthenticated) {
            // while redirection
            this._router.navigate([
              route.queryParamMap.get('from') || '/'
            ]);
            return false;
          }

          // trying to extract the authentication result from query params
          const authResult: IAuthResult = {
            status: route.queryParamMap.get(AuthResultQueryParamKey.Status) as AuthStatus,
            accessToken: route.queryParamMap.get(AuthResultQueryParamKey.AccessToken),
          };

          // if there's no authentication result - go to auth page
          if (!authResult.status) {
            return true;
          }

          // if there's a token - store it
          this._tokenService.setToken(authResult.accessToken);

          // if we're successfully authenticated - go to redirect page or home
          this._router.navigate([
            route.queryParamMap.get(AuthResultQueryParamKey.From) || '/'
          ]);

          return false;

        })
      );
  }

}
