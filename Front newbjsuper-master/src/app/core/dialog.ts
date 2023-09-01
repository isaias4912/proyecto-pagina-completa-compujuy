import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { Input, EventEmitter, Component } from '@angular/core';
import { ComponentPage } from './component-page';
import { Confirmation } from '../modules/shared/models/confirmation';
import { ConfirmationServiceService } from '../modules/shared/services/confirmation-service.service';

@Component({
  template: ''
})
export class Dialog extends ComponentPage {
  confirmation: Confirmation;
  subscription: Subscription;
  @Input()
  key: string;
  @Input() message: string = 'Acepta la acción?';

  constructor(public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService) {
    super();
    this.subscription = this.confirmationService.requireConfirmation$.subscribe(confirmation => {
      if (confirmation.key === this.key) {
        this.confirmation = confirmation;
        if (this.confirmation.accept) {
          this.confirmation.acceptEvent = new EventEmitter();
          this.confirmation.acceptEvent.subscribe(this.confirmation.accept);
        }
        if (this.confirmation.reject) {
          this.confirmation.rejectEvent = new EventEmitter();
          this.confirmation.rejectEvent.subscribe(this.confirmation.reject);
        }
        if (this.confirmation.save) {
          this.confirmation.saveEvent = new EventEmitter();
          this.confirmation.saveEvent.subscribe(this.confirmation.save);
        }
        if (this.confirmation.update) {
          this.confirmation.updateEvent = new EventEmitter();
          this.confirmation.updateEvent.subscribe(this.confirmation.update);
        }
        if (this.confirmation.select) {
          this.confirmation.selectEvent = new EventEmitter();
          this.confirmation.selectEvent.subscribe(this.confirmation.select);
        }
        if (this.confirmation.cancel) {
          this.confirmation.cancelEvent = new EventEmitter();
          this.confirmation.cancelEvent.subscribe(this.confirmation.cancel);
        }
        this.message = this.confirmation.message || this.message;
      }
    });
  }

  public cancel(data:any=null) {
    if (this.confirmation && this.confirmation.cancelEvent) {
      this.confirmation.cancelEvent.emit(data);
    }
    this.activeModal.close();
    this.confirmation = null;
  }

  public acept(data:any=null) {
    if (this.confirmation && this.confirmation.acceptEvent) {
      this.confirmation.acceptEvent.emit(data);
    }
    this.activeModal.close();
    this.confirmation = null;
  }

  public update() {
    if (this.confirmation && this.confirmation.updateEvent) {
      this.confirmation.updateEvent.emit();
    }
    this.activeModal.close();
    this.confirmation = null;
  }

  public save(data:any=null) {
    if (this.confirmation && this.confirmation.saveEvent) {
      this.confirmation.saveEvent.emit(data);
    }
    this.activeModal.close();
    this.confirmation = null;
  }
  public select() {
    if (this.confirmation && this.confirmation.selectEvent) {
      this.confirmation.selectEvent.emit();
    }
    this.activeModal.close();
    this.confirmation = null;
  }
}
