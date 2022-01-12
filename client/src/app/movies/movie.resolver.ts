import { MoviesService } from '@sc/movies/movies.service';
import { IMovie } from '@sc/movies/movie.interface';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Resolve,
} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MovieResolver implements Resolve<IMovie> {

  constructor(private readonly _moviesService: MoviesService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovie> {
    return this._moviesService.getById(route.params.id);
  }

}
