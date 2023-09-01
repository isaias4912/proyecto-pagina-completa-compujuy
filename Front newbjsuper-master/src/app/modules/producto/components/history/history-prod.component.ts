import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { Response } from 'src/app/core/services/utils/response';
import { Producto } from 'src/app/core/models/producto';
import { UtilPage } from 'src/app/core/util-page';

declare var $: any;

@Component({
  selector: 'history-prod',
  templateUrl: './history-prod.component.html',
  styleUrls: ['./history-prod.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class HistoryProdComponent extends UtilPage implements OnInit, AfterViewInit {

  private dataPages = {
    page: 0,
    pagesize: 10
  }
  public tooltip: 'tooltip';
  public finish = false;
  public loading = false;
  private idProducto: string;
  public historial = [];
  public producto$: Observable<Response<Producto>>;
  throttle = 300;
  scrollDistance = 1;
  scrollUpDistance = 2;
  direction = '';
  constructor(
    public productosHTTPService: ProductosHTTPService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }
  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.idProducto = this.decrypt(params.get("idProducto"));
        // if (this.idProducto) {
        //   this.getProducto(this.idProducto);
        // }
      });
    this.producto$ = this.productosHTTPService.getProductoMinById(this.idProducto);
  }

  ngAfterViewInit(): void {
    // this.dataPages.page++;
    this.loadMore();

  }

  loadMore() {
    // if (!this.finish) {
    //   if (!this.loading) {
    this.dataPages.page++;
    this.loading = true;
    this.productosHTTPService.getHistorialProducto(this.idProducto, this.dataPages).subscribe((response) => {
      this.finish = response.data.length <= 0;
      if (!this.finish) {
        this.renderHistorial(response);
      } else {
        this.cdr.markForCheck();
      }
    });
    // }
    // }
  }

  renderHistorial(response) {
    for (let i = 0; i <= response.data.length - 1; i++) {
      if (this.historial.length <= 0) {
        this.historial.push({
          id: i,
          dia: response.data[i].dia,
          event: [response.data[i]]
        });
      } else {
        if (this.historial[this.historial.length - 1].dia == response.data[i].dia) {
          this.historial[this.historial.length - 1].event.push(response.data[i]);
        } else {
          this.historial.push({
            id: i,
            dia: response.data[i].dia,
            event: [response.data[i]]
          });
        }
      }
    }
    if (this.historial.length > 0) {
      let startTime = moment(this.historial[0].dia, 'DD-MM-YYYY');
      let iscurrentDate = startTime.isSame(new Date(), "day");
      if (iscurrentDate) {
        this.historial[0].dia = "Hoy";
      }
    }
    this.cdr.markForCheck();
  }


}
