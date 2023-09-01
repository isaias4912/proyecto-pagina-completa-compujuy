import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Response } from '../../../../../core/services/utils/response';
import { tap, switchMap } from 'rxjs/operators';
import { Producto } from '../../../../../core/models/producto';
import { Dialog } from '../../../../../core/dialog';
import { DetailProductoComponent } from '../../../modules/shared-prod/components/detail-product/detail-product.component';


@Component({
  selector: 'parents-modal',
  templateUrl: './parents-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ParentsModalComponent extends Dialog implements OnInit, AfterViewInit {
  public filterMovimiento: any = {
    ventas: false,
    asociados: false,
    sucursales: []
  };
  public producto: Producto;
  public parents$: Observable<Response<any>>
  public refresh = new BehaviorSubject<void>(null);
  public dataParentChild: any = null;

  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal,

  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.parents$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getParentsAndChilds(this.producto.id.toString()).pipe(
      tap(resp => {
        this.dataParentChild = { ...resp.data };
      })
    )));
  }
  ngAfterViewInit(): void {
    this.title = "Padres e hijos de producto | " + this.producto.nombreFinal;
  }

  selectParentOrChild(producto: any) {
    const modalNewProduct = this.modalService.open(DetailProductoComponent, { size: 'xl', backdrop: 'static' });
    modalNewProduct.componentInstance.idProducto = producto.id;
  }
}

