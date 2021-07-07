export interface IMovie {
  id: number;
  adult: boolean;
  voteCount: number;
  voteAverage: number;
  popularity: number;
  genres: string[];
  releaseDate: Date;
  posterPath: string;
  overview: string;
  originalTitle: string;
  originalLanguage: string;
  title: string;
  backdropPath: string;
}

export interface IMovieLookupDTO {
  id: number;
  genres: string[];
  posterPath: string;
  overview: string;
  title: string;
}
