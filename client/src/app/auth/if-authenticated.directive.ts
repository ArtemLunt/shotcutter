import { Directive, EmbeddedViewRef, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthFacadeService } from '@sc/auth/auth-facade.service';
import { AutoUnsubscribe } from '@sc/shared/decorators';
import { map } from 'rxjs/operators';

@Directive({
  selector: '[scIfAuthenticated]'
})
@AutoUnsubscribe()
export class IfAuthenticatedDirective implements OnInit {

  @Input()
  scIfAuthenticated: boolean = true;

  private _embeddedViewRef: EmbeddedViewRef<any>;

  constructor(
    private readonly _authService: AuthFacadeService,
    private _templateRef: TemplateRef<any>,
    private _viewContainer: ViewContainerRef
  ) { }

  ngOnInit(): void {
    this._authService.isAuthenticated()
      .pipe(
        map(isAuthenticated => isAuthenticated === this.scIfAuthenticated)
      )
      .subscribe(shouldShowItem => {
        if (shouldShowItem) {
          this._embeddedViewRef = this._viewContainer.createEmbeddedView(this._templateRef);
        } else if (this._embeddedViewRef) {
          this._embeddedViewRef.destroy();
        }
      })
      .registerFor(this);
  }

}
