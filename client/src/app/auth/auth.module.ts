import { IfAuthenticatedDirective } from './if-authenticated.directive';
import {AuthRoutingModule} from '@sc/auth/auth-routing.module';
import {AuthComponent} from '@sc/auth/auth.component';
import {SharedModule} from '@sc/shared';
import {NgModule} from '@angular/core';

const DIRECTIVES = [IfAuthenticatedDirective];

@NgModule({
    declarations: [AuthComponent, ...DIRECTIVES],
    exports: [
      ...DIRECTIVES
    ],
    imports: [
        AuthRoutingModule,
        SharedModule,
    ]
})
export class AuthModule {
}
