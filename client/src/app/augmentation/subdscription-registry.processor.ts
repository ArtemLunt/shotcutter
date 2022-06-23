import { Type } from '@angular/core';
import { Subscription } from 'rxjs';

/**
 * @description simple map, which contains all the registered subscriptions
 *
 * Tip: by design it should take a class instance as a key
 */
const subscriptionRegistry: Map<any, Subscription[]> = new Map();

/**
 * @description implementation of {@see Subscription.registerFor} method
 *
 * Note: we do need to leave it right here to keep subscriptionRegistry {@see subscriptionRegistry}
 * encapsulated in the same closure
 */
export function registerFor(registerKey: any): any {
  // if there's no subscriptions list for this key - we should create it
  if (!subscriptionRegistry.has(registerKey)) {
    subscriptionRegistry.set(registerKey, []);
  }

  // here we're pushing the new subscription into the subscriptions array
  subscriptionRegistry.get(registerKey).push(this as any);
}

/**
 * @description implementation of the @AutoUnsubscribe decorator
 * here we're simply taking a subscriptions list for specified class instance
 * from subscriptionRegistry {@see subscriptionRegistry},
 * unsubscribing from the whole list and removing this list.
 *
 * Note: we do need to leave it right here to keep subscriptionRegistry {@see subscriptionRegistry}
 * encapsulated in the same closure
 *
 * @see AutoUnsubscribe
 */
export function AutoUnsubscribeSubscriptionRegistryProcessor(): ClassDecorator {
  return (target: any) => {
    const targetClass: Type<any> = target;
    // here we're taking an original ngOnDestroy function to call it if it's already implemented
    const originalDestroyFunction: () => void = (targetClass as any).prototype.ngOnDestroy;

    // wrapping up the ngOnDestroy hook to cleanup the subscriptions for current class instance
    targetClass.prototype.ngOnDestroy = function(): void {
      if (originalDestroyFunction) {
        originalDestroyFunction.bind(this)();
      }

      // cleanup
      subscriptionRegistry.get(this)?.forEach(sub => sub.unsubscribe());
      subscriptionRegistry.delete(this);
    } as any;
  };
}
