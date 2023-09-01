import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { ClienteHTTPService } from '../../../../../../core/services/http/cliente-http.service';
import { pluck, map } from 'rxjs/operators';



@Injectable()
export class SelectCliService {
  private cache: any = {};
  private cliente = new Subject<any>();
  clienteChange$ = this.cliente.asObservable();

  constructor(
    private clienteHTTPService: ClienteHTTPService
  ) { }

  loadData(id: number | string): Observable<any> {
    if (!id) {
      this.cliente.next(null);
      return of([]).pipe(map(res => {
        return null;
      }));
    }
    if (this.cache[id]) {
      this.cliente.next(this.cache[id]);
      return of(this.cache[id]);
    } else {
      return this.clienteHTTPService.getClienteMin(id).pipe(pluck('data')
      // , map((resp: any) => {
      //   let filterMto: any = {};
      //   filterMto.cliente = new Cliente();;
      //   filterMto.cliente.id = resp.id;
      //   filterMto.cliente.persona = new Persona();
      //   filterMto.cliente.persona.nombre = resp.nombre;
      //   filterMto.cliente.persona.apellido = resp.apellido;
      //   filterMto.cliente.persona.dni = resp.dni;
      //   filterMto.cliente.persona.cuil = resp.cuil;
      //   filterMto.cliente.cuentaCorriente = { id: resp.idCuentaCorriente };
      //   this.cliente.next(filterMto.cliente);
      //   return filterMto.cliente;
      // })
      );
    }
  }
  saveData(id: any, data: any) {
    this.cache[id] = data;
  }
}
