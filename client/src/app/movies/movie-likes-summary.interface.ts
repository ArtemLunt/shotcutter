export interface IMovieLikesSummary {
  relatedMovieId?: string;
  likedByCurrentUser?: boolean;
  dislikedByCurrentUser?: boolean;
  amountOfLikes?: number;
  amountOfDislikes?: number;
}
