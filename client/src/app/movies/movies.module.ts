import { MoviesRoutingModule } from '@sc/movies/movies-routing.module';
import { MovieComponent } from '@sc/movies/movie/movie.component';
import { MovieState } from '@sc/movies/movie/state/movie.state';
import { MovieResolver } from '@sc/movies/movie.resolver';
import { CommonModule } from '@angular/common';
import { PipesModule } from '@sc/shared/pipes';
import { SharedModule } from '@sc/shared';
import { NgxsModule } from '@ngxs/store';
import { NgModule } from '@angular/core';
import { AuthModule } from '@sc/auth';


@NgModule({
  declarations: [MovieComponent],
  imports: [
    MoviesRoutingModule,
    CommonModule,
    SharedModule,
    PipesModule,
    NgxsModule.forFeature([MovieState]),
    AuthModule
  ],
  providers: [
    MovieResolver
  ]
})
export class MoviesModule {
}
