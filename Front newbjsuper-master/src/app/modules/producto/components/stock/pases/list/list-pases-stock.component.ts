import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Producto } from '../../../../../../core/models/producto';
import { pluck, tap, switchMap } from 'rxjs/operators';
import { ComponentPage } from '../../../../../../core/component-page';
import { SelectSucursalSimpleComponent } from 'src/app/modules/shared/components/sucursal/select-simple/select-scursal-simple.component';
import { ConfirmInputComponent } from 'src/app/modules/shared/components/confirm/comfirm-input/confirm-input.component';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { SelectProdComponent } from 'src/app/modules/producto/modules/shared-prod/components/select/select-prod.component';


@Component({
  selector: 'list-pases-stock',
  templateUrl: './list-pases-stock.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListPasesStockComponent extends ComponentPage implements OnInit, AfterViewInit {
  public filterPase: any = { producto: null, sucursal: null, activo: 2 };
  public producto: Producto;
  public movimientos: any = {};
  public pases$: Observable<any>
  private refresh = new BehaviorSubject<void>(null);
  @ViewChild(SelectSucursalSimpleComponent, { static: false })
  selectSucursalSimpleComponent: SelectSucursalSimpleComponent;
  @ViewChild(SelectProdComponent, { static: false })
  selectProdComponent: SelectProdComponent;
  public dataOptions = {
    filterActivo: [{ value: 0, name: "No aceptados" }, { value: 1, name: "Aceptados" }, { value: 2, name: "Todos" }]
  };
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }

  ngOnInit() {
    this.pases$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getPasesByPage(this.paramPagination, this.filterPase).pipe(
      tap(resp => {
        this.setValuePagination(resp);
      }), pluck('data'), tap(resp => { })
    )));

  }
  ngAfterViewInit(): void {
    this.title = "Lista de pases registrados";
    this.reset();
  }
  confirmPase(pase: any) {
    let modalConfirim = this.modalService.open(ConfirmInputComponent, { size: 'lg', backdrop: 'static' });
    modalConfirim.componentInstance.title = "Acepta el pase " //+ producto.nombreFinal;
    modalConfirim.componentInstance.label = "Detalle del pase:" //+ producto.nombreFinal;
    modalConfirim.componentInstance.value = pase.descripcion; //+ producto.nombreFinal;
    this.confirmationService.confirm({
      accept: (data) => {
        pase.descripcion = data;
        this.productosHTTPService.confirmPase(pase).subscribe(resp => {
          this.messageToast.success('Pase confirmado correctamente');
          this.pageChanged();
        });
      },
      cancel: () => {

      }
    });
  }
  removePase(pase: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación del pase',
      accept: () => {
        this.productosHTTPService.deletePase(pase.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el pase');
          this.pageChanged();
        });
      },
      cancel: () => {

      }
    });
  }
  pageChanged() {
    this.refresh.next();
  }
  reset() {
    this.filterPase = { producto: null, sucursal: null, activo: 2 };
    setTimeout(() => {
      this.selectProdComponent.resetData();
      this.selectSucursalSimpleComponent.resetData();
      $("#txtProducto").focus();
    });
  }

}

