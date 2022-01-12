import {Component, ChangeDetectionStrategy} from '@angular/core';
import {AuthFacadeService} from '@sc/auth';
import {CurrentUserState} from '@sc/user/state';
import {Router} from '@angular/router';
import {Select} from '@ngxs/store';
import {Observable} from 'rxjs';
import {IUser} from '@sc/user';

@Component({
  selector: 'sc-user-details-menu',
  templateUrl: './user-details-menu.component.html',
  styleUrls: ['./user-details-menu.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserDetailsMenuComponent {

  @Select(CurrentUserState.currentUser)
  readonly currentUser$: Observable<IUser>;

  constructor(
    private readonly _authFacadeService: AuthFacadeService,
    private readonly _router: Router
  ) {
  }

  logout(): void {
    this._authFacadeService.removeToken();
    this._router.navigate(['/auth']);
  }

}
