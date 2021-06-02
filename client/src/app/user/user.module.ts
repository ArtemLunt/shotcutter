import {CurrentUserState} from '@sc/user/state';
import {SharedModule} from '@sc/shared';
import {NgModule} from '@angular/core';
import {NgxsModule} from '@ngxs/store';

@NgModule({
  declarations: [],
  imports: [
    SharedModule,
    NgxsModule.forFeature([
      CurrentUserState
    ])
  ]
})
export class UserModule {
}
