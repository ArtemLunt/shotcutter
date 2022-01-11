import { MainComponent } from '@sc/main/main.component';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { AuthGuard } from '@sc/auth';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'movie',
        loadChildren: () => import('@sc/movies').then(m => m.MoviesModule),
        canActivate: [AuthGuard],
      },
      {
        path: 'profile',
        loadChildren: () => import('@sc/profile').then(m => m.ProfileModule)
      },
      {
        path: 'search',
        loadChildren: () => import('@sc/search').then(m => m.SearchModule)
      },
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
