import { filter, first } from 'rxjs/operators';
import { OperatorFunction } from 'rxjs';

export function firstTruthy(): OperatorFunction<any, any> {
  return source => source.pipe(
    filter(item => !!item),
    first()
  );
}
