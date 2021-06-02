import {registerFor} from '@sc/augmentation';
import {Subscription} from 'rxjs';
import 'rxjs';

declare module 'rxjs' {
  interface Subscription {
    /**
     * @description this method basically allows us to register this subscription for specified registrationKey
     * This method used for {@see AutoUnsubscribe} decorator:
     * @see AutoUnsubscribeSubscriptionRegistryProcessor
     */
    registerFor(registrationKey: any): void;
  }
}

Subscription.prototype.registerFor = registerFor;
