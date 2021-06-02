import {ProfileRoutingModule} from '@sc/profile/profile-routing.module';
import {ProfileComponent} from '@sc/profile/profile.component';
import {TypedFormsModule} from '@sc/shared/typed-forms';
import {CommonModule} from '@angular/common';
import {SharedModule} from '@sc/shared';
import {NgModule} from '@angular/core';

@NgModule({
  declarations: [ProfileComponent],
  imports: [
    ProfileRoutingModule,
    TypedFormsModule,
    CommonModule,
    SharedModule,
  ]
})
export class ProfileModule {
}
