import { TokenService } from '@sc/auth/token.service';
import { EventService } from '@sc/shared/services';
import { EventType } from '@sc/shared/enums';
import { merge, Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthFacadeService {

  constructor(
    private readonly _tokenService: TokenService,
    private readonly _eventService: EventService
  ) {
  }

  isAuthenticated(): Observable<boolean> {
    return merge(
      of(this._tokenService.hasToken()),
      this._eventService.eventsByType<void>(EventType.Unauthenticated)
        .pipe(map(() => false))
    );
  }

  isUnauthenticated(): Observable<boolean> {
    return merge(
      of(!this._tokenService.hasToken()),
      this._eventService.eventsByType<void>(EventType.Unauthenticated)
        .pipe(map(() => true))
    );
  }

  removeToken(): void {
    this._tokenService.removeToken();
    this._eventService.dispatch(EventType.Unauthenticated);
  }

}
