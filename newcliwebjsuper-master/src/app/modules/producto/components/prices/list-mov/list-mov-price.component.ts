import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { Dialog } from '../../../../../core/dialog';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, BehaviorSubject } from 'rxjs';
import { Response } from '../../../../../core/services/utils/response';
import { Producto } from '../../../../../core/models/producto';
import { pluck, tap, switchMap } from 'rxjs/operators';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';


@Component({
  selector: 'list-mov-price',
  templateUrl: './list-mov-price.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListMovPriceComponent extends Dialog implements OnInit, AfterViewInit {
  public producto: Producto;
  public movimientos: any = {};
  public movimientos$: Observable<Array<any>>
  private refresh = new BehaviorSubject<void>(null);

  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal,
  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.movimientos$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getMovimientosPrecioByProducto(this.paramPagination, this.producto.id.toString()).pipe(
      tap(resp => {
        this.setValuePagination(resp);
      }), pluck('data'), tap(resp => { })
    )));

  }
  pageChanged() {
    this.refresh.next();
  }
  ngAfterViewInit(): void {
    this.title = "Movimientos de precio de producto | " + this.producto.nombreFinal;
  }

}

