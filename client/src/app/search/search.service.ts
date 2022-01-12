import { ISearchParams } from '@sc/search/search-params.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { IPage } from '@sc/shared/interfaces/page.interface';
import { SCApiEndpoints } from '@sc/shared/enums';
import { Injectable } from '@angular/core';
import { IMovie } from '@sc/movies';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private readonly _http: HttpClient) {
  }

  searchMovies(
    searchParams: ISearchParams,
    page: number = 0,
  ): Observable<IPage<IMovie>> {
    const params = Object.entries(searchParams)
      .reduce(
        (httpParams, [key, value]) => (
          httpParams.append(key, value)
        ),
        new HttpParams()
      )
      .append('page', page.toString());

    return this._http.get<IPage<IMovie>>(`${SCApiEndpoints.SearchMovies}`, { params });
  }

}
