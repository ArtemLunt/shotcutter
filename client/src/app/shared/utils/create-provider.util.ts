import { Provider } from '@angular/core';

export function createConstProvider<T>(constant: {[key in keyof T]: any}): Provider {
  return {
    provide: constant,
    useValue: constant
  }
}
