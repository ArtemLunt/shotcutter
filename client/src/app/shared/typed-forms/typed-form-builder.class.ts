import {TypedFormGroup} from '@sc/shared/typed-forms/typed-form-group.class';
import {TypedFormsModule} from '@sc/shared/typed-forms/typed-forms.module';
import {AbstractControlOptions, FormBuilder} from '@angular/forms';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: TypedFormsModule
})
export class TypedFormBuilder extends FormBuilder {
  group<T>(controlsConfig: { [p in keyof T]: any }, options?: AbstractControlOptions | null): TypedFormGroup<T> {
    return super.group(controlsConfig, options) as TypedFormGroup<T>;
  }
}
