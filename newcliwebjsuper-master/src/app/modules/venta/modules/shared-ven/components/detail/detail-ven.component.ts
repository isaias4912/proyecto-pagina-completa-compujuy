import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { pluck, switchMap, tap } from 'rxjs/operators';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { UtilPage } from '../../../../../../core/util-page';

@Component({
  selector: 'detail-ven',
  templateUrl: './detail-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailVenComponent extends UtilPage implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  venta$: Observable<any>;
  @Input()
  public data: any = null;
  private refresh = new BehaviorSubject<void>(null);
  private cbte = null;
  constructor(
    private ventaHTTPService: VentaHTTPService
    ) {
      super();
    }
    
    ngOnInit() {
    this.venta$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        iif(() => this.idEntity != null || this.idEntity != undefined
          , this.ventaHTTPService.getVentaDetalle(this.idEntity).pipe(pluck('data'))
          , of(this.data).pipe(tap(() => {
            this.data = null; // la primera  vez no mas deberia completar los datos
          }))
        )
      ), tap(resp => {
        this.cbte = resp;
      })
    );
  }
  getListaPrecio(venta: any) {
    return JSON.parse(venta.listaPrecio).nombre;
  }
  getCbte() {
    return this.cbte;
  }
  ngAfterViewInit(): void {
  }

}

