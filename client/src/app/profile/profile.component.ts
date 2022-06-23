import { UpdateUserAction, CurrentUserState, UpdateAvatarAction } from '@sc/user/state';
import { delay, distinctUntilChanged, map, startWith } from 'rxjs/operators';
import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { TypedFormBuilder, TypedFormGroup } from '@sc/shared/typed-forms';
import { IEditableUserPart, IUser, UserService } from '@sc/user';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AutoUnsubscribe } from '@sc/shared/decorators';
import { firstTruthy } from '@sc/shared/operators';
import { combineLatest, Observable } from 'rxjs';
import { Comparators } from '@sc/shared/utils';
import { Select, Store } from '@ngxs/store';

@Component({
  selector: 'sc-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe()
export class ProfileComponent implements OnInit {

  get currentUser(): IUser {
    return this._store.selectSnapshot(CurrentUserState.currentUser);
  }

  constructor(
    private readonly _formBuilder: TypedFormBuilder,
    private readonly _userService: UserService,
    private readonly _snackbar: MatSnackBar,
    private readonly _store: Store
  ) {
    this.controlKeys = { username: 'username' };
  }

  private static readonly AVAILABLE_AVATAR_TYPES: Set<string> = new Set<string>()
    .add('jpg')
    .add('jpeg')
    .add('png')
    .add('gif');

  @Select(CurrentUserState.currentUser)
  readonly currentUser$: Observable<IUser>;
  @Select(CurrentUserState.isAvatarUpdating)
  readonly isAvatarUpdating$: Observable<boolean>;

  readonly controlKeys: { [key in keyof IEditableUserPart]: keyof IEditableUserPart };

  isSubmitDisabled$: Observable<boolean>;
  userInfoForm: TypedFormGroup<IEditableUserPart>;

  ngOnInit(): void {
    this.currentUser$
      .pipe(firstTruthy())
      .subscribe(({ username }) => {
        this.userInfoForm = this._formBuilder.group<IEditableUserPart>({
          username: [username]
        });

        this.isSubmitDisabled$ = combineLatest([this.currentUser$, this.userInfoForm.valueChanges])
          .pipe(
            map(([currentUser, updatedUser]) => (
              !currentUser || Comparators.deepComparePr(
                this._userService.extractEditablePart(currentUser),
                updatedUser
              )
            )),
            distinctUntilChanged(),
            startWith(true)
          );
      }).registerFor(this);
  }

  uploadNewAvatar(avatar: File): void {
    const fileExtension: string = avatar.name.split('.').pop();

    if (!ProfileComponent.AVAILABLE_AVATAR_TYPES.has(fileExtension)) {
      this._snackbar.open('Please, upload the JPG, PNG or GIF file', null, {
        duration: 2000
      });
      return;
    }

    this._store.dispatch(new UpdateAvatarAction(avatar))
      .subscribe(() => {
        this._snackbar.open('Your avatar was successfully updated', null, {
          duration: 2000
        });
      }).registerFor(this);
  }

  submitForm(): void {
    this._store.dispatch(new UpdateUserAction(this.userInfoForm.value))
      .pipe(delay(100))
      .subscribe(() => {
        this._snackbar.open('Your information was successfully updated', null, {
          duration: 2000
        });
      }).registerFor(this);
  }

}
