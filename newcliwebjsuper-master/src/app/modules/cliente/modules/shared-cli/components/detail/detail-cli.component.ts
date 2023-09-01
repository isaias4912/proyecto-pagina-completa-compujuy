import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { ClienteHTTPService } from '../../../../../../core/services/http/cliente-http.service';

@Component({
  selector: 'detail-cli',
  templateUrl: './detail-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailCliComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  cliente$: Observable<any>;

  constructor(
    private clienteHTTPService: ClienteHTTPService,
  ) {
  }

  ngOnInit() {
    this.cliente$ = this.clienteHTTPService.getCliente(this.idEntity).pipe(pluck('data'));
  }

  ngAfterViewInit(): void {
  }

}

