import {MoviesRoutingModule} from '@sc/movies/movies-routing.module';
import {MovieComponent} from '@sc/movies/movie/movie.component';
import {MovieResolver} from '@sc/movies/movie.resolver';
import { CommonModule } from '@angular/common';
import {PipesModule} from '@sc/shared/pipes';
import { NgModule } from '@angular/core';
import {SharedModule} from '@sc/shared';


@NgModule({
  declarations: [MovieComponent],
  imports: [
    MoviesRoutingModule,
    CommonModule,
    SharedModule,
    PipesModule
  ],
  providers: [
    MovieResolver
  ]
})
export class MoviesModule { }
