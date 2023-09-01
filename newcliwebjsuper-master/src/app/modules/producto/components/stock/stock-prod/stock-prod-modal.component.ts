import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Response } from '../../../../../core/services/utils/response';
import { tap, switchMap, pluck } from 'rxjs/operators';
import { Producto } from '../../../../../core/models/producto';
import { Dialog } from '../../../../../core/dialog';


@Component({
  selector: 'stock-prod-modal',
  templateUrl: './stock-prod-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StockProdModalComponent extends Dialog implements OnInit, AfterViewInit {

  public producto: Producto;
  public listStock$: Observable<any>
  public refresh = new BehaviorSubject<void>(null);

  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal,

  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.listStock$ = this.refresh.asObservable().pipe(switchMap(() =>
      this.productosHTTPService.getStockByProducto(this.producto.id.toString()).pipe(pluck('data'))
    )
    );
  }
  ngAfterViewInit(): void {
    this.title = "Listado de stock | " + this.producto.nombreFinal;
  }

}

