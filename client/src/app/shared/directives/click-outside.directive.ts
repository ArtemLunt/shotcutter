import {Directive, ElementRef, EventEmitter, OnInit, Output} from '@angular/core';
import { AutoUnsubscribe } from '@sc/shared/decorators';
import {skip} from 'rxjs/operators';
import {fromEvent} from 'rxjs';

@Directive({
  selector: '[scClickOutside]'
})
@AutoUnsubscribe()
export class ClickOutsideDirective implements OnInit {

  @Output()
  readonly scClickOutside: EventEmitter<void>;

  constructor(private _elementRef: ElementRef) {
    this.scClickOutside = new EventEmitter<void>();
  }

  ngOnInit(): void {
    fromEvent(document, 'click')
      .pipe(skip(1))
      .subscribe(evt => {
        if (!this._elementRef.nativeElement.contains(evt.target)) {
          this.scClickOutside.emit();
        }
      })
      .registerFor(this);
  }

}
