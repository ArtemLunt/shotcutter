import {HomeRoutingModule} from '@sc/home/home-routing.module';
import {HomeComponent} from '@sc/home/home.component';
import { NgModule } from '@angular/core';
import {SharedModule} from '@sc/shared';
import {UserModule} from '@sc/user';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    HomeRoutingModule,
    SharedModule,
    UserModule
  ]
})
export class HomeModule { }
