import { HttpClient } from '@angular/common/http';
import { SCApiEndpoints } from '@sc/shared/enums';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenresService {

  constructor(private readonly _http: HttpClient) {
  }

  getGenres(): Observable<string[]> {
    return this._http.get<string[]>(SCApiEndpoints.Genres);
  }

}
