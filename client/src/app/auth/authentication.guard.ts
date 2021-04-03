import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {AuthResultQueryParamKey} from '@sc/auth/auth-result.interface';
import {AuthenticationService} from '@sc/auth/authentication.service';
import { Injectable } from '@angular/core';
import {tap} from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(
    private readonly _authenticationService: AuthenticationService,
    private readonly _router: Router,
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this._authenticationService.isAuthenticated()
      .pipe(
        tap(isAuthenticated => {
          if (isAuthenticated) {
            return;
          }

          this._router.navigate(['../auth'], {
            queryParams: {
              [AuthResultQueryParamKey.From]: state.url
            }
          });
        })
      );
  }

}
