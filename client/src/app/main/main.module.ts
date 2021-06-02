import {HeaderComponent, UserInfoComponent} from '@sc/main/header';
import {MainRoutingModule} from '@sc/main/main-routing.module';
import {MainComponent} from '@sc/main/main.component';
import {SharedModule} from '@sc/shared';
import {NgModule} from '@angular/core';
import {UserModule} from '@sc/user';
import { UserDetailsMenuComponent } from './header/user-details-menu/user-details-menu.component';

@NgModule({
  declarations: [
    MainComponent,
    HeaderComponent,
    UserInfoComponent,
    UserDetailsMenuComponent
  ],
  imports: [
    MainRoutingModule,
    SharedModule,
    UserModule
  ]
})
export class MainModule {
}
