import {MovieComponent} from '@sc/movies/movie/movie.component';
import {MovieResolver} from '@sc/movies/movie.resolver';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

const routes: Routes = [
  {
    path: ':id',
    component: MovieComponent,
    resolve: {
      movie: MovieResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MoviesRoutingModule {
}
