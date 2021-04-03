import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import {LoadCurrentUserAction} from '@sc/user/state';
import {Store} from '@ngxs/store';

@Component({
  selector: 'sc-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent implements OnInit {

  constructor(private readonly _store: Store) { }

  ngOnInit(): void {
    this._store.dispatch(new LoadCurrentUserAction());
  }

}
