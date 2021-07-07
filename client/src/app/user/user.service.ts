import { HttpClient, HttpHeaders } from '@angular/common/http';
import {SCApiEndpoints} from '@sc/shared/enums';
import {IEditableUserPart, IUser} from '@sc/user/user.interface';
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

  patchUser(userId: string, patchObject: IEditableUserPart): Observable<IUser> {
    return this._http.patch<IUser>(`${SCApiEndpoints.CurrentUser}`, patchObject);
  }

  updateAvatar(avatar: File): Observable<IUser> {
    const formData = new FormData();
    formData.append('avatar', avatar);
    return this._http.patch<IUser>(`${SCApiEndpoints.CurrentUser}/avatar`, formData);
  }

  extractEditablePart({username}: IUser): IEditableUserPart {
    return {username};
  }

}
