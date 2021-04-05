import {State, Action, StateContext, NgxsOnInit, Selector} from '@ngxs/store';
import {LoadCurrentUserAction} from '@sc/user/state/user.actions';
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
export class UserState implements NgxsOnInit {

  @Selector()
  static currentUser(state: UserStateModel): IUser {
    return state?.currentUser;
  }

  constructor(private _userService: UserService) {
  }

  ngxsOnInit(ctx?: StateContext<any>): void {
    ctx.dispatch(new LoadCurrentUserAction());
  }

  @Action(LoadCurrentUserAction)
  loadCurrentUser(
    {patchState}: StateContext<UserStateModel>
  ): Observable<IUser> {
    return this._userService.getCurrentUser()
      .pipe(tap(currentUser => patchState({currentUser})));
  }

}
