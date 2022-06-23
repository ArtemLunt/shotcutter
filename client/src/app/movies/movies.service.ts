import { IMovieLikesSummary } from '@sc/movies/movie-likes-summary.interface';
import { IMovie, IMovieLookupDTO } from '@sc/movies/movie.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SCApiEndpoints } from '@sc/shared/enums';
import { SearchQueryParam } from '@sc/search';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  constructor(private readonly _http: HttpClient) {
  }

  getById(id: string): Observable<IMovie> {
    return this._http.get<IMovie>(`${SCApiEndpoints.Movies}/${id}`);
  }

  getMovieLikesSummary(id: number): Observable<IMovieLikesSummary> {
    return this._http.get<IMovieLikesSummary>(`${SCApiEndpoints.Likes}/${id}`);
  }

  likeMovie(relatedMovieId: number, value: boolean): Observable<IMovieLikesSummary> {
    return this._http.put<IMovieLikesSummary>(`${SCApiEndpoints.Like}/${relatedMovieId}`, {}, {
      params: new HttpParams().append('value', value)
    });
  }

  deleteLike(relatedMovieId: number): Observable<IMovieLikesSummary> {
    return this._http.delete<IMovieLikesSummary>(`${SCApiEndpoints.Like}/${relatedMovieId}`);
  }

  lookup(key: string): Observable<IMovieLookupDTO[]> {
    const params = new HttpParams().append(SearchQueryParam.Query, key);
    return this._http.get<IMovieLookupDTO[]>(SCApiEndpoints.LookupMovies, { params });
  }

}
