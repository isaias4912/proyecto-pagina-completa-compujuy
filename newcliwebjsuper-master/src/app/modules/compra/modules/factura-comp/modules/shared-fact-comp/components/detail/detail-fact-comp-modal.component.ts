import { Component, OnInit, AfterViewInit,  ChangeDetectionStrategy, OnDestroy } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Dialog } from '../../../../../../../../core/dialog';
import { ConfirmationServiceService } from '../../../../../../../shared/services/confirmation-service.service';
import { Observable } from 'rxjs';
import { pluck, tap, take } from 'rxjs/operators';
import { EncMovimiento } from '../../../../../../../../core/models/acc-enc-mov';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';

@Component({
  selector: 'detail-fact-comp-modal',
  templateUrl: './detail-fact-comp-modal.component.html',
  styleUrls: ['./detail-fact-comp-modal.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailFactCompModalComponent extends Dialog implements OnInit, AfterViewInit, OnDestroy {

  invoice: EncMovimiento;
  invoice$: Observable<any>;
  private idInvoice: number;

  constructor(
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
    private compraHTTPService: CompraHTTPService
  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.invoice$ = this.compraHTTPService.getFactura(this.idInvoice.toString()).pipe(take(1), pluck('data'),
      tap(resp => {
        this.invoice = resp;
      })
    );
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
  }
}

