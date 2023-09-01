import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck, tap } from 'rxjs/operators';
import { CajaHTTPService } from '../../../../core/services/http/caja-http.service';

@Component({
  selector: 'detail-caja',
  templateUrl: './detail-caja.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailCajaComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  caja$: Observable<any>;
  dataCbtes = [
    { id: 1, text: "Comprobantes", selected: false },
    { id: 2, text: "Facturas electrónicas", selected: false },
    { id: 3, text: "Tiques fiscales", selected: false }
  ];
  private cbtesHab = [];
  constructor(
    private cajaHTTPService: CajaHTTPService,
  ) {
  }

  ngOnInit() {
    this.caja$ = this.cajaHTTPService.getCaja(this.idEntity).pipe(pluck('data'), tap(resp => {
      let comprobantes = JSON.parse(resp.comprobantesHab);
      this.dataCbtes.forEach(c1 => {
        comprobantes.forEach((c2) => {
          if (c1.id == c2) {
            c1.selected = true;
          }
        });
      });
    }
    ));
  }

  ngAfterViewInit(): void {
  }

}

