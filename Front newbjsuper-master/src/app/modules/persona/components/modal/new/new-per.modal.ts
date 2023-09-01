import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild } from '@angular/core';
import { Dialog } from '../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { NewPerComponent } from '../../new/new-per.component';
import { IDataForm } from 'src/app/core/models/idata-form';
declare var $: any;

@Component({
  selector: 'new-per-modal',
  templateUrl: './new-per.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewPerModal extends Dialog implements AfterViewInit {
  @ViewChild(NewPerComponent, { static: false })
  private newPerComponent: NewPerComponent;
  public dataForm: IDataForm = { submitted: false };
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
    this.key='NewPerModal';
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }
  savePer() {
    this.dataForm = { ...this.dataForm, submitted: true };
    this.newPerComponent.save(1);
  }
  close() {
    this.activeModal.dismiss();
  }
  saveSucces(data) {
   this.activeModal.close(data.data);
  }
}

