import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';

export class TypedFormControl<T> extends FormControl {

  valueChanges: Observable<T>;
  value: T;

}
