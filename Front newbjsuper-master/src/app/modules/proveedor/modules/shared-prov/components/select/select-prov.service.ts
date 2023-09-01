import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { pluck, map, tap } from 'rxjs/operators';
import { ProveedorHTTPService } from 'src/app/core/services/http/proveedor-http.service';



@Injectable()
export class SelectProvService {
  private cache: any = {};
  private proveedor = new Subject<any>();
  proveedorChange$ = this.proveedor.asObservable();

  constructor(
    private proveedorHTTPService: ProveedorHTTPService
  ) { }

  loadData(id: number | string): Observable<any> {
    if (!id) {
      this.proveedor.next(null);
      return of([]).pipe(map(res => {
        return null;
      }));
    }
    if (this.cache[id]) {
      this.proveedor.next(this.cache[id]);
      return of(this.cache[id]);
    } else {
      return this.proveedorHTTPService.getProveedorMin(id).pipe(pluck('data'), tap((resp: any) => {
        this.proveedor.next(resp);
      }));
    }
  }
  saveData(id: any, data: any) {
    this.cache[id] = data;
  }
}
