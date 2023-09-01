import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FamiliaHTTPService } from './services/http/familia-http.service';
import { ProductosHTTPService } from './services/http/product-http.service';
import { ProductoPadreHTTPService } from './services/http/producto-padre-http.service';
import { AuthService } from './services/auth/auth.service';
import { AuthGuardService } from './services/auth/auth-guard.service';
import { UserHTTPService } from './services/user/user-http.service';
import { StorageService } from './services/utils/storage.service';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { BreadcrumbService } from './components/breadcrumb/breadcrumb.service';
import { UtilHTTPService } from './services/http/util-http.service';
import { MainHTTPService } from './services/http/main-http';
import { ProveedorHTTPService } from './services/http/proveedor-http.service';
import { EmpresaHTTPService } from './services/http/empresa-http.service';
import { EntidadHTTPService } from './services/http/entidad-http.service';
import { CtaCteHTTPService } from './services/http/cta-cte-http.service';
import { CtaCteProvHTTPService } from './services/http/cta-cte-prov-http.service';
import { PersonaHTTPService } from './services/http/persona-http.service';
import { UsuarioHTTPService } from './services/http/usuario-http.service';
import { UsuarioPublicHTTPService } from './services/http/usuario-public-http.service';
import { ClienteHTTPService } from './services/http/cliente-http.service';
import { VentaHTTPService } from './services/http/venta-http.service';
import { CajaHTTPService } from './services/http/caja-http.service';
import { OfertaHTTPService } from './services/http/oferta-http.service';
import { ConfigHTTPService } from './services/http/config-http.service';
import { CompraHTTPService } from './services/http/compra-http.service';
import { LoaderService } from './services/utils/loader.service';
import { SharedModule } from '../modules/shared/shared.module';
import { RouterModule } from '@angular/router';
import { PresupuestoHTTPService } from './services/http/presupuesto-http.service';



@NgModule({
  declarations: [
    BreadcrumbComponent
  ],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule
  ],
  exports: [
    BreadcrumbComponent,
  ],
  entryComponents: [
  ],
  providers: [
    AuthService,
    AuthGuardService,
    ProductosHTTPService,
    FamiliaHTTPService,
    ProductoPadreHTTPService,
    UserHTTPService,
    StorageService,
    UtilHTTPService,
    MainHTTPService,
    ProveedorHTTPService,
    EmpresaHTTPService,
    UtilHTTPService,
    PersonaHTTPService,
    UsuarioHTTPService,
    ClienteHTTPService,
    CtaCteHTTPService,
    VentaHTTPService,
    CajaHTTPService,
    OfertaHTTPService,
    CompraHTTPService,
    CtaCteProvHTTPService,
    ConfigHTTPService,
    LoaderService,
    EntidadHTTPService,
    BreadcrumbService,
    UsuarioPublicHTTPService,
    PresupuestoHTTPService
  ]
})
export class CoreModule { }
