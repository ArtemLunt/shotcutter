import {AbstractControl, FormGroup} from '@angular/forms';

export class TypedFormGroup<T> extends FormGroup {

  readonly value: T;
  controls: {
    [key in keyof T]: AbstractControl;
  };


}
