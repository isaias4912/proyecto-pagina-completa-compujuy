import { Injectable } from '@angular/core';
import { UserLogged } from '../../models/user-logged';
import { User } from '../../models/user';
import { HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { tap, catchError } from 'rxjs/operators';
import { BehaviorSubject, throwError } from 'rxjs';
import { MenuPrincipal } from 'src/app/core/models/menu-principal';
import { environment } from 'src/environments/environment';
import { ServiceService } from '../service.service';
import { StorageService } from '../utils/storage.service';


const httpOptions = {
  headers: new HttpHeaders({
    'Authorization': 'Basic Y2xpZW50ZXdlYjpzb21lc2VjcmV0',
  })
};
// const token = localStorage.getItem('access_token');

@Injectable()
// export class AuthService extends ServiceService {
export class AuthService extends ServiceService {

  private logged = new BehaviorSubject<boolean>(false);
  private userLogged = new BehaviorSubject<UserLogged>(null);
  private menuPrincipal = new BehaviorSubject<MenuPrincipal>(null);
  public menuPrincipal$ = this.menuPrincipal.asObservable();
  public logged$ = this.logged.asObservable();
  public userLogged$ = this.userLogged.asObservable();
  private configuracion: any = {};
  private sucursales: Array<any> = new Array<any>();

  constructor(
    public jwtHelper: JwtHelperService,
    private storageService: StorageService
  ) {
    super();
  }

  login(user: User) {
    let input = new FormData();
    input.append("username", user.username);
    input.append("password", user.password);
    input.append("grant_type", user.grant_type);
    input.append("scope", user.scope);
    return this.http.post<any>(environment.baseURL + "oauth/token", input, httpOptions).pipe(
      tap(res => {
        localStorage.setItem('access_token', res.access_token);
        this.logged.next(true);
      })
    );
  }
  recuperarPass(data:any) {
    return this.http.post<any>(environment.baseURL+ 'public/user/recuperarpass', data);
  }
  updatePass(data:any) {
    return this.http.post<any>(environment.baseURL+ 'public/user/recuperarpass/save', data);
  }
  validateToken(token:string) {
    return this.http.get<any>(environment.baseURL+ 'public/user/validate/'+token);
  }

  logout() {
    let token = localStorage.getItem('access_token');
    return this.http.post<any>(environment.baseURL + "api/v1/auth/token/revoke", { token: token }).pipe(
      tap(res => {
        this.logged.next(false);
        this.clear();
      }),
      catchError((error) => {
        return throwError(error);
      })
    );
  };

  public isAuthenticated(): boolean {
    let resp = false;
    let token = localStorage.getItem('access_token');
    if (this.jwtHelper.isTokenExpired(token)) {
      this.logged.next(false);
      return false;
    } else {
      this.logged.next(true);
      return true;
    }
  }
  /**
   * devuelve el usuario logueado
   */
  public getUserLogged(): UserLogged {
    let token = localStorage.getItem('access_token');
    let user = this.jwtHelper.decodeToken(token);
    let userLogged = new UserLogged();
    userLogged.idUser = user.id;
    userLogged.username = user.user_name;
    userLogged.authorities = user.authorities;
    userLogged.idSucursal = user.id_suc;
    userLogged.idApp = user.app_id;
    userLogged.gravatar = (user.key_avatar == null || user.key_avatar == "") ? "images/user.png" : "https://www.gravatar.com/avatar/" + user.key_avatar + "?s=52&d=identicon";

    return userLogged;
  }

  public getConfiguracionGral() {
    return this.http.get<any>(environment.baseAPIURL + 'util/configuraciongral');
  }
  public setConfiguracion(configuracion: any) {
    this.configuracion = configuracion;
  }

  public getConfiguracion() {
    return this.configuracion;
  }

  public setSucursales(sucursales: any) {
    this.sucursales = sucursales;
  }

  public getSucursales() {
    return this.sucursales;
  }
  public ifExistRol(roles: Array<string>) {
    let rolesLogged = this.getUserLogged().authorities;
    let exist = false;
    if (roles) {
      roles.forEach(rol => {
        rolesLogged.forEach(rolLogged => {
          if (rol == rolLogged) {
            exist = true;
          }
        });
      });
    }
    return exist;
  }
  public clear() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('menuJSuper');
    this.clearConfig();
  }
  
  public clearConfig(){
    this.storageService.removeMainData();
    this.setConfiguracion(null);
    this.setSucursales(null);
  }
}
