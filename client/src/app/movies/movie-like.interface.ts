export interface IMovieLike {
  id: string;
  relatedUserId: string;
  relatedMovieId: number;
  value: boolean;
  updateTime: Date;
}
