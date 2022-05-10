import { LoadUserAction, UpdateUserAction, UpdateAvatarAction } from '@sc/user/state/current-user.actions';
import { State, Action, StateContext, NgxsOnInit, Selector } from '@ngxs/store';
import { UserService } from '@sc/user/user.service';
import { IUser } from '@sc/user/user.interface';
import { AuthFacadeService } from '@sc/auth';
import { filter, tap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export class CurrentUserStateModel {
  currentUser: IUser;
  isAvatarUpdating: boolean;
  isUserUpdating: boolean;
}

@State<CurrentUserStateModel>({
  name: 'currentUser',
  defaults: {
    currentUser: null,
    isAvatarUpdating: false,
    isUserUpdating: false
  }
})
@Injectable()
export class CurrentUserState implements NgxsOnInit {

  @Selector()
  static currentUser({ currentUser }: CurrentUserStateModel): IUser {
    return currentUser;
  }

  @Selector()
  static isAvatarUpdating({ isAvatarUpdating }: CurrentUserStateModel): boolean {
    return isAvatarUpdating;
  }

  constructor(private _userService: UserService, private _authService: AuthFacadeService) {
  }

  ngxsOnInit(ctx?: StateContext<any>): void {
    this._authService.isAuthenticated()
      .pipe(filter(isAuthenticated => isAuthenticated))
      .subscribe(() => {
        ctx.dispatch(new LoadUserAction());
      });
  }

  @Action(LoadUserAction)
  loadUser(
    { patchState }: StateContext<CurrentUserStateModel>
  ): Observable<IUser> {
    return this._userService.getCurrentUser()
      .pipe(
        tap(currentUser => patchState({ currentUser }))
      );
  }

  @Action(UpdateUserAction)
  updateUser(
    { patchState, getState }: StateContext<CurrentUserStateModel>,
    { patchObj }: UpdateUserAction
  ): Observable<IUser> {
    const { currentUser: { id } } = getState();
    return this._userService.patchUser(id, patchObj)
      .pipe(tap(currentUser => patchState({ currentUser })));
  }

  @Action(UpdateAvatarAction)
  updateAvatar(
    { patchState, getState }: StateContext<CurrentUserStateModel>,
    { avatar }: UpdateAvatarAction
  ): Observable<IUser> {
    patchState({ isAvatarUpdating: true });
    return this._userService.updateAvatar(avatar)
      .pipe(tap(currentUser => patchState({ currentUser, isAvatarUpdating: false })));
  }

}
