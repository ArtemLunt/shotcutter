import { map, mergeMap } from 'rxjs/operators';
import { Observable } from 'rxjs';

export function tapAsync<T = any>(
  callback: (source$: any) => Observable<any>
): (source$: Observable<T>) => Observable<T> {
  return source$ => source$.pipe(
    mergeMap(res => (
      callback(res).pipe(map(() => res))
    ))
  )
}
