import { Directive, ElementRef, Input } from '@angular/core';
import { fromEvent, Subscription } from 'rxjs';
import { AutoUnsubscribe } from '@sc/shared/decorators';

@Directive({
  selector: '[scStopPropagation]'
})
@AutoUnsubscribe()
export class StopPropagationDirective {

  private _sub: Subscription;

  @Input()
  set scEventType(eventType: string) {
    if (this._sub) {
      this._sub.unsubscribe();
      this._sub = null;
    }

    this._sub = fromEvent(this._elem.nativeElement, eventType)
      .subscribe((evt: Event) => evt.stopImmediatePropagation());
    this._sub.registerFor(this);
  }

  constructor(private readonly _elem: ElementRef) {
  }

}
