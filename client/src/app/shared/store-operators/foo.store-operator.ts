import { StateOperator } from '@ngxs/store';

export function foo(): StateOperator<any> {
  return item => item;
}
