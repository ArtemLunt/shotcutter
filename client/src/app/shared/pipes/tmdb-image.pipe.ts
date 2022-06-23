import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tmdbImage'
})
export class TmdbImagePipe implements PipeTransform {

  private static readonly TMDB_IMAGE_BASE_URL: string = 'https://image.tmdb.org/t/p/w';

  transform(path: string, width = 500): string {
    return `${TmdbImagePipe.TMDB_IMAGE_BASE_URL}${width}/${path}`;
  }

}
