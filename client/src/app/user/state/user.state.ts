import {LoadCurrentUserAction} from '@sc/user/state/user.actions';
import {State, Action, StateContext} from '@ngxs/store';
import {UserService} from '@sc/user/user.service';
import {IUser} from '@sc/user/user.interface';
import {Injectable} from '@angular/core';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

export class UserStateModel {
  currentUser: IUser;
}

@State<UserStateModel>({
  name: 'user',
  defaults: {
    currentUser: null
  }
})
@Injectable()
export class UserState {

  constructor(
    private _userService: UserService
  ) {
  }

  @Action(LoadCurrentUserAction)
  loadCurrentUser(
    {patchState}: StateContext<UserStateModel>
  ): Observable<IUser> {
    return this._userService.getCurrentUser()
      .pipe(tap(currentUser => patchState({currentUser})));
  }

}
