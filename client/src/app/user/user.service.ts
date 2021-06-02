import {HttpClient} from '@angular/common/http';
import {SCApiEndpoints} from '@sc/shared/enums';
import {IUser} from '@sc/user/user.interface';
import {environment} from '@sc/environments';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http: HttpClient) {
  }

  getCurrentUser(): Observable<IUser> {
    return this._http.get<IUser>(`${SCApiEndpoints.CurrentUser}`);
  }

}
