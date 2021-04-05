import {Component, ChangeDetectionStrategy} from '@angular/core';

@Component({
  selector: 'sc-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {

  showDetailsMenu: boolean;

  constructor() {
    this.showDetailsMenu = false;
  }

}
