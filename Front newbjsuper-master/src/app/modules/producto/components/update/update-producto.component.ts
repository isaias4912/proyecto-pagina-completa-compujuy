import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
// import { Producto } from 'src/app/core/models/producto';
import { IDataFormProducto } from '../../models/idata-form-producto';
// import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { Observable } from 'rxjs';
import { tap, switchMap } from 'rxjs/operators';
import { ParamMap, RoutesRecognized, ActivatedRoute } from '@angular/router';
import { Producto } from 'src/app/core/models/producto';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { FieldsProductoComponent } from '../fields/fields-producto.component';
import { Response } from 'src/app/core/services/utils/response';
import { BreadcrumbService } from 'src/app/core/components/breadcrumb/breadcrumb.service';
declare var $: any;

@Component({
  selector: 'update-producto',
  templateUrl: './update-producto.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateProductoComponent extends ComponentPage implements OnInit, AfterViewInit {
  public dataNewUpdate$: Observable<Producto[]>;
  public producto$: Observable<Response<Producto>>;
  public dataNewUpdate: any;
  public dataForm: IDataFormProducto;
  public idProducto: string;
  public producto: Producto;;
  @ViewChild(FieldsProductoComponent, { static: false })
  fieldsProductoComponent: FieldsProductoComponent;
  @Input()
  tipoProducto: 'SIMPLE' | 'COMPUESTO' = 'SIMPLE';
  constructor(
    private activatedRoute: ActivatedRoute,
    private productosHTTPService: ProductosHTTPService,
    private breadcrumbService: BreadcrumbService
  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.idProducto = this.decrypt(params.get("idProducto"));
      });

    this.producto$ = this.productosHTTPService.getDataNewUpdate().pipe(tap(resp => {
      this.dataNewUpdate = resp.data;
    }), switchMap(() => this.productosHTTPService.getProductoById(this.idProducto).pipe(
      tap((resp: Response<Producto>) => {
        this.producto = resp.data;
        this.breadcrumbService.changeCustomText(this.producto.nombreFinal);
      })
    ))
    );
  }

  ngAfterViewInit(): void {
  }
  save(data: any) {
    this.productosHTTPService.updateProducto(data.producto).subscribe(response => {
      this.messageToast.success("Se modificó correctamente el producto :" + response.data.id + "-" + response.data.nombre);
      if (data.option == 2) {// volver
        this.location.back();
      }
    });
  }
  cancel() {
    this.location.back();
  }
}

