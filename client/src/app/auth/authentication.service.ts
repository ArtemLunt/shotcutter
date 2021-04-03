import {TokenService} from '@sc/auth/token.service';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private readonly _tokenService: TokenService) {
  }

  isAuthenticated(): Observable<boolean> {
    return of(this._tokenService.hasToken());
  }

}
