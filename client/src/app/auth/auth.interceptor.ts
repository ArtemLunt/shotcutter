import {AuthFacadeService} from '@sc/auth/auth-facade.service';
import { Injectable } from '@angular/core';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {EMPTY, Observable} from 'rxjs';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from '@angular/common/http';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private _authService: AuthFacadeService,
    private _router: Router
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(catchError(this._handleError.bind(this)));
  }

  private _handleError(): Observable<any> {
    this._authService.removeToken();
    this._router.navigate(['/auth']);

    return EMPTY;
  }

}
