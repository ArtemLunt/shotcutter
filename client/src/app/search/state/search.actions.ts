import { ISearchParams } from '@sc/search';

export class SearchAction {
  static readonly type = '[Search] Search movies';
  constructor(public readonly searchParams: ISearchParams) { }
}

export class CleanSearchResultsAction {
  static readonly type = '[Search] Clean search results';
}
