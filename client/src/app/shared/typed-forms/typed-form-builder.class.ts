import {TypedFormGroup} from '@sc/shared/typed-forms/typed-form-group.class';
import {TypedFormsModule} from '@sc/shared/typed-forms/typed-forms.module';
import {AbstractControlOptions, AsyncValidatorFn, FormBuilder, ValidatorFn} from '@angular/forms';
import {Injectable} from '@angular/core';
import {TypedFormControl} from '@sc/shared/typed-forms/typed-form-control.class';

@Injectable({
  providedIn: TypedFormsModule
})
export class TypedFormBuilder extends FormBuilder {

  group<T>(controlsConfig: { [p in keyof T]: any }, options?: AbstractControlOptions | null): TypedFormGroup<T> {
    return super.group(controlsConfig, options) as TypedFormGroup<T>;
  }

  control<T>(
    formState: T,
    validatorOrOpts?: ValidatorFn | ValidatorFn[] | AbstractControlOptions | null,
    asyncValidator?: AsyncValidatorFn | AsyncValidatorFn[] | null)
    : TypedFormControl<T> {
    return super.control(formState, validatorOrOpts, asyncValidator);
  }

}
