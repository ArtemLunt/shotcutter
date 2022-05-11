import { EventType } from '@sc/shared/enums';
import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { filter } from 'rxjs/operators';

type Event<T = any> = {
  type: EventType,
  payload?: T
}

@Injectable({
  providedIn: 'root'
})
export class EventService {
  readonly events$: Observable<Event>;

  private _events$: Subject<Event>;

  constructor() {
    this._events$ = new Subject();
    this.events$ = this._events$.asObservable();
  }

  dispatch(type: EventType, payload?: any): void {
    this._events$.next({ type, payload });
  }

  eventsByType<T = any>(expectedType: EventType): Observable<Event<T>> {
    return this._events$
      .pipe(filter(({ type }) => type === expectedType))
  }

}
