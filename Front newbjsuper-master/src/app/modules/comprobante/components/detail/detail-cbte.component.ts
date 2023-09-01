import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { pluck, switchMap, tap } from 'rxjs/operators';
import { TipoCbte } from 'src/app/core/enums';
import { PresupuestoHTTPService } from 'src/app/core/services/http/presupuesto-http.service';
import { VentaHTTPService } from 'src/app/core/services/http/venta-http.service';
import { Response } from 'src/app/core/services/utils/response';
import { UtilPage } from 'src/app/core/util-page';

@Component({
  selector: 'detail-cbte',
  templateUrl: './detail-cbte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailCbteComponent extends UtilPage implements OnInit, AfterViewInit {

  TipoCbte = TipoCbte;
  @Input()
  tipoCbte: TipoCbte= TipoCbte.CBTE_VENTA;
  @Input()
  public idEntity: number;
  cbtes$: Observable<any>;
  @Input()
  public data: any = null;
  private refresh = new BehaviorSubject<void>(null);
  private cbte = null;
  private $cbteDetail:Observable<Response<any>> = null;
  constructor(
    private ventaHTTPService: VentaHTTPService,
    private presupuestoHTTPService: PresupuestoHTTPService
  ) {
    super();
  }

  ngOnInit() {
    if (this.tipoCbte== TipoCbte.CBTE_VENTA){
      this.$cbteDetail= this.ventaHTTPService.getVentaDetalle(this.idEntity);
    }
    if (this.tipoCbte== TipoCbte.CBTE_PRESUPUESTO){
      this.$cbteDetail= this.presupuestoHTTPService.getPresupuestoDetalle(this.idEntity);
    }
    this.cbtes$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        iif(() => this.data == null || this.data == undefined
          , this.$cbteDetail.pipe(pluck('data'))
          , of(this.data).pipe(tap(() => {
            this.data = null; // la primera  vez no mas deberia completar los datos
          }))
        )
      ), tap(resp => {
        console.log('resp', resp)
        this.cbte = resp;
      })
    );
  }
  getListaPrecio(venta: any) {
    return JSON.parse(venta.listaPrecioData).nombre;
  }
  getCbte() {
    return this.cbte;
  }
  ngAfterViewInit(): void {
  }

}

