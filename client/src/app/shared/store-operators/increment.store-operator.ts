import { StateOperator } from '@ngxs/store';

export function increment(decrement: boolean = false): StateOperator<number> {
  return initialState => decrement ? initialState - 1 : initialState + 1;
}
