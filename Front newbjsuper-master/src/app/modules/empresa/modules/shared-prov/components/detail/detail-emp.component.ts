import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { ProveedorHTTPService } from '../../../../../../core/services/http/proveedor-http.service';
import { EmpresaHTTPService } from 'src/app/core/services/http/empresa-http.service';

@Component({
  selector: 'detail-emp',
  templateUrl: './detail-emp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailEmpComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  empresa$: Observable<any>;

  constructor(
    private empresaHTTPService: EmpresaHTTPService,
  ) {
  }

  ngOnInit() {
    this.empresa$ = this.empresaHTTPService.getEmpresa(this.idEntity).pipe(pluck('data'));
  }

  ngAfterViewInit(): void {
  }

}

