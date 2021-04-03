import {TokenService} from '@sc/auth/token.service';
import {HttpHeaderKey} from '@sc/shared/enums';
import {environment} from '@sc/environments';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpEvent,
} from '@angular/common/http';
import {CookieKey} from '@sc/auth/cookie-key.enum';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private readonly _tokenService: TokenService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    /*if (!environment.local) {
      return next.handle(request);
    }*/

    const updatedRequest = request.clone({
      headers: request.headers
        .append(HttpHeaderKey.Authorization, this._tokenService.getToken()),
      withCredentials: true
    });

    return next.handle(updatedRequest);
  }

}
