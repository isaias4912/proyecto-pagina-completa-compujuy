import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Confirmation} from '../models/confirmation';

// @Injectable({
//   providedIn: 'root'
// })
@Injectable()
export class ConfirmationServiceService {

  private requireConfirmationSource = new Subject<Confirmation>();
  private acceptConfirmationSource = new Subject<Confirmation>();

  requireConfirmation$ = this.requireConfirmationSource.asObservable();
  accept = this.acceptConfirmationSource.asObservable();

  confirm(confirmation: Confirmation) {
    this.requireConfirmationSource.next(confirmation);
    return this;
  }

  onAccept() {
    this.acceptConfirmationSource.next(null);
  }

}
