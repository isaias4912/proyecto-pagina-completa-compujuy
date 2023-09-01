import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ServiceService } from '../service.service';
import { retry, catchError, delay } from 'rxjs/operators';
import { MenuPrincipal } from '../../models/menu-principal';
import { Response } from '../utils/response'

@Injectable()
export class UserHTTPService extends ServiceService {

  constructor() {
    super();
  }
  /**
   * Retornna menu, pero primero busca si es que hay algo en el local storage
   */
  public getMenu(): Observable<Response<MenuPrincipal>> {
    let tempMenu = JSON.parse(localStorage.getItem('menuJSuper'));
    if (tempMenu) {
      let menuPrincipal = new MenuPrincipal();
      menuPrincipal = <MenuPrincipal>tempMenu;
      return of(<Response<MenuPrincipal>>(new Response<MenuPrincipal>(menuPrincipal))).pipe(delay(100));
    } else {
      return this.http.get<Response<MenuPrincipal>>(this.BASE_URL + "api/v1/usuarios/menu");
    }
  }
}
