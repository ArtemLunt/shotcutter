import {RouterModule, Routes} from '@angular/router';
import {AuthGuard, AuthPageGuard} from '@sc/auth';
import {NgModule} from '@angular/core';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('@sc/main').then(m => m.MainModule)
  },
  {
    path: 'auth',
    loadChildren: () => import('@sc/auth').then(m => m.AuthModule),
    canActivate: [AuthPageGuard]
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
