import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { ProveedorHTTPService } from '../../../../../../core/services/http/proveedor-http.service';

@Component({
  selector: 'detail-prov',
  templateUrl: './detail-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailProvComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  proveedor$: Observable<any>;

  constructor(
    private proveedorHTTPService: ProveedorHTTPService,
  ) {
  }

  ngOnInit() {
    this.proveedor$ = this.proveedorHTTPService.getProveedor(this.idEntity).pipe(pluck('data'));
  }

  ngAfterViewInit(): void {
  }

}

