import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Producto } from 'src/app/core/models/producto';
import { IDataFormProducto } from '../../models/idata-form-producto';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { FieldsProductoComponent } from '../fields/fields-producto.component';
declare var $: any;

@Component({
  selector: 'new-producto',
  templateUrl: './new-producto.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewProductoComponent extends ComponentPage implements OnInit, AfterViewInit {
  public dataNewUpdate$: Observable<Producto[]>;
  public dataNewUpdate: any;
  public dataForm: IDataFormProducto;
  public producto: Producto = new Producto();
  @ViewChild(FieldsProductoComponent, { static: false })
  fieldsProductoComponent: FieldsProductoComponent;
  @Input()
  tipoProducto: 'SIMPLE' | 'COMPUESTO' = 'SIMPLE';
  constructor(
    private productosHTTPService: ProductosHTTPService,
  ) {
    super();
  }

  ngOnInit() {
    this.dataForm = {
      informacionBasica: { submitted: false },
      imagenes: { submitted: false },
      precios: { submitted: false },
      existencia: {
        codigosBarra: { submitted: false },
        stockSucursal: { submitted: false },
        proveedores: { submitted: false }
      },
      productoCompuesto: { submitted: false }
    };
    this.dataNewUpdate$ = this.productosHTTPService.getDataNewUpdate().pipe(tap(resp => {
      this.dataNewUpdate = resp.data;
    }));
  }

  ngAfterViewInit(): void {
  }
  save(data: any) {
    this.productosHTTPService.saveProducto(data.producto).subscribe(response => {
      this.messageToast.success("Se agrego correctamente el producto :" + response.data.id + "-" + response.data.nombre);
      if (data.option == 1) {
        this.fieldsProductoComponent.reset();
      }
      if (data.option == 2) {// volver
        this.location.back();
      }
    });
  }
  cancela() {
    this.location.back();
  }
}

