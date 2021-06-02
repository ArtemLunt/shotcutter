import {AuthRoutingModule} from '@sc/auth/auth-routing.module';
import {AuthComponent} from '@sc/auth/auth.component';
import {SharedModule} from '@sc/shared';
import {NgModule} from '@angular/core';

@NgModule({
  declarations: [AuthComponent],
  imports: [
    AuthRoutingModule,
    SharedModule,
  ]
})
export class AuthModule {
}
