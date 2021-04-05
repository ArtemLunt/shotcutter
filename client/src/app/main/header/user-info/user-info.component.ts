import {Component, ChangeDetectionStrategy} from '@angular/core';
import {UserState} from '@sc/user/state';
import {Select} from '@ngxs/store';
import {Observable} from 'rxjs';
import {IUser} from '@sc/user';

@Component({
  selector: 'sc-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserInfoComponent {

  @Select(UserState.currentUser)
  readonly currentUser$: Observable<IUser>;

}
