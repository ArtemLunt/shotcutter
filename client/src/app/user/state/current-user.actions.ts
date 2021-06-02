import {IEditableUserPart, IUser} from '@sc/user';

export class LoadUserAction {
  static readonly type = '[Current User] Load current user';
}

export class SetUserAction {
  static readonly type = '[Current User] Set current user';
  constructor(public readonly currentUser: IUser) {
  }
}

export class UpdateUserAction {
  static readonly type = '[Current User] Update current user';
  constructor(public readonly patchObj: IEditableUserPart) {
  }
}

export class UpdateAvatarAction {
  static readonly type = '[Current User] Update avatar';
  constructor(public readonly avatar: File) {
  }
}
