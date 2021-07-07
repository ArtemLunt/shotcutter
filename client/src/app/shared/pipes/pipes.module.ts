import {UrlCssValuePipe} from '@sc/shared/pipes/url-css-value.pipe';
import {MaxLengthPipe} from '@sc/shared/pipes/max-length.pipe';
import {TmdbImagePipe} from '@sc/shared/pipes/tmdb-image.pipe';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';

const PIPES = [
  UrlCssValuePipe,
  MaxLengthPipe,
  TmdbImagePipe,
];

@NgModule({
  declarations: [...PIPES],
  exports: [...PIPES],
  imports: [
    CommonModule
  ]
})
export class PipesModule {
}
