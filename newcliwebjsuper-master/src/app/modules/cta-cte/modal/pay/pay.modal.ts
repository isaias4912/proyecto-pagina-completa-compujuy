import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild, Input } from '@angular/core';
import { Dialog } from '../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { PayComponent } from '../../components/pay/pay.component';
import { IDataForm } from '../../../../core/models/idata-form';
import { LoaderService } from 'src/app/core/services/utils/loader.service';

declare var $: any;

@Component({
  selector: 'pay-modal',
  templateUrl: './pay.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PayModal extends Dialog implements AfterViewInit {

  @Input()
  public title:string="Pagos";
  @Input()
  public type: 'CTA_CTE_CLI' | 'CTA_CTE_PROV';
  public dataFilter = { };
  public dataPago = { };
  @ViewChild(PayComponent, { static: false })
  private payComponent: PayComponent;
  public dataForm: IDataForm = { submitted: false };
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private loaderService: LoaderService
  ) {
    super(activeModal, confirmationService);
    this.key='PayModal';
  }
  ngAfterViewInit(): void {
    
  }
  ngOnInit(): void {
  }
  savePay() {
    this.dataForm = { ...this.dataForm, submitted: true };
    console.log('this.dataForm', this.dataForm)
    if (this.type=='CTA_CTE_CLI'){
      this.payComponent.savePayCli(1);
    }
    if (this.type=='CTA_CTE_PROV'){
      this.payComponent.savePayProv(1);
    }
  }
  saveSucces(data) {
   this.activeModal.close(data.data);
  }
}

