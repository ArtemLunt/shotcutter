import { Directive, ElementRef, Input } from '@angular/core';
import { fromEvent, Subscription } from 'rxjs';

@Directive({
  selector: '[scStopPropagation]'
})
export class StopPropagationDirective {

  private _sub: Subscription;

  @Input()
  set scEventType(eventType: string) {
    if (this._sub) {
      this._sub.unsubscribe();
      this._sub = null;
    }

    this._sub = fromEvent(this._elem.nativeElement, eventType)
      .subscribe((evt: Event) => {
        debugger;
        evt.stopImmediatePropagation();
      });
    this._sub.registerFor(this);
  }

  constructor(private readonly _elem: ElementRef) {
  }

}
