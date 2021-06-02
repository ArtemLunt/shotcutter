export type AuthStatus = 'failure' | 'success';

export enum AuthResultQueryParamKey {
  Status = 'status',
  AccessToken = 'access_token',
  From = 'from'
}

export interface IAuthResult {
  status: AuthStatus;
  accessToken?: string;
}
