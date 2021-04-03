import {AuthenticationGuard, AuthenticationPageGuard} from '@sc/auth';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('@sc/home').then(m => m.HomeModule),
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'auth',
    loadChildren: () => import('@sc/auth').then(m => m.AuthModule),
    canActivate: [AuthenticationPageGuard]
  },
  {
    path: '**',
    redirectTo: 'home'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
