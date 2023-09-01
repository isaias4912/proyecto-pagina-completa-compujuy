import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { Dialog } from 'src/app/core/dialog';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Response } from 'src/app/core/services/utils/response';
import { Producto } from 'src/app/core/models/producto';
import { pluck, tap, switchMap } from 'rxjs/operators';
import { SelectSucursalComponent } from 'src/app/modules/shared/components/sucursal/select-multiple/select-scursal.component';
import { DetailFactCompModalComponent } from 'src/app/modules/compra/modules/factura-comp/modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailVenComponent } from 'src/app/modules/venta/modules/shared-ven/components/detail/detail-ven.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';


@Component({
  selector: 'list-mov-stock',
  templateUrl: './list-mov-stock.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListMovStockComponent extends Dialog implements OnInit, AfterViewInit {
  public filterMovimiento: any = {
    ventas: false,
    asociados: false,
    sucursales: []
  };
  public producto: Producto;
  public movimientos: any = {};
  public movimientos$: Observable<Array<any>>
  private refresh = new BehaviorSubject<void>(null);
  @ViewChild(SelectSucursalComponent, { static: false })
  selectSucursal: SelectSucursalComponent;
  public listStock$: Observable<any>

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
    this.movimientos$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getMovimientosByProducto(this.paramPagination, this.producto.id.toString(), this.filterMovimiento).pipe(
      tap(resp => {
        this.setValuePagination(resp);
      }), pluck('data'), tap(resp => { })
    )));

  }
  pageChanged() {
    this.filterMovimiento.sucursales = this.selectSucursal.getDataInt();
    this.refresh.next();
  }
  ngAfterViewInit(): void {
    this.title = "Listado de movimientos de producto | " + this.producto.nombreFinal;
  }
  showDetailInvoice(data: any) {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = data.id;
  }
  detailVenta(entity: any) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailVenComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle de la venta | ' + entity.id;
  }
}

