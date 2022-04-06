import { IMovieLikesSummary } from '@sc/movies';

export class SetMovieLikesSummaryAction {
  static readonly type = '[Movie] Set likes summary';

  constructor(public readonly likesSummary: IMovieLikesSummary) {
  }
}

export class LikeMovieAction {
  static readonly type = '[Movie] Like movie';

  constructor(
    public readonly relatedMovieId: number,
    public readonly value: boolean
  ) {
  }
}

export class DeleteLikeAction {
  static readonly type = '[Movie] Delete like';

  constructor(public readonly relatedMovieId: number) {
  }
}
