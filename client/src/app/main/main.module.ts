import { UserDetailsMenuComponent } from '@sc/main/header/user-details-menu/user-details-menu.component';
import { HeaderComponent, UserInfoComponent } from '@sc/main/header';
import { MainRoutingModule } from '@sc/main/main-routing.module';
import { TypedFormsModule } from '@sc/shared/typed-forms';
import { MainComponent } from '@sc/main/main.component';
import { PipesModule } from '@sc/shared/pipes';
import { SharedModule } from '@sc/shared';
import { NgModule } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { GenresState } from '@sc/genres';
import { UserModule } from '@sc/user';

@NgModule({
  declarations: [
    UserDetailsMenuComponent,
    UserInfoComponent,
    HeaderComponent,
    MainComponent,

  ],
  imports: [
    NgxsModule.forFeature([GenresState]),
    MainRoutingModule,
    TypedFormsModule,
    SharedModule,
    PipesModule,
    UserModule,
  ]
})
export class MainModule {
}
