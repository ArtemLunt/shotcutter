import {IEnv} from '@sc/environments/environment.interface';

export const environment: IEnv = {
  production: false,
  local: window.location.hostname === 'localhost',
  ssoUrl: 'http://localhost:8081'
};
