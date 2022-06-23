import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'urlCssValue'
})
export class UrlCssValuePipe implements PipeTransform {

  transform(value: string): unknown {
    return `url('${value}')`;
  }

}
