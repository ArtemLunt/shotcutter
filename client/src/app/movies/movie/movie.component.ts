import {Component, ChangeDetectionStrategy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {IMovie} from '@sc/movies';
import {Observable} from 'rxjs';

@Component({
  selector: 'sc-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MovieComponent {

  readonly movie$: Observable<IMovie>;

  constructor(route: ActivatedRoute) {
    this.movie$ = route.data
      .pipe(map(({movie}) => movie));
  }

}
