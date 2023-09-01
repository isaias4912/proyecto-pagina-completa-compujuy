import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main.component';
import { HomeComponent } from './components/home/home.component';
import { AuthGuardService } from 'src/app/core/services/auth/auth-guard.service';
const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthGuardService],
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Inicio',
              url: null
            }
          ]
        }
      },
      {
        path: 'home2',
        component: HomeComponent,
        canActivate: [AuthGuardService],
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Inicio',
              url: null
            }
          ]
        }
      },
    
      {
        path: '',
        redirectTo: 'home',
      },
      {
        path: 'productos',
        loadChildren: () => import('../../modules/producto/producto.module').then(m => m.ProductoModule),
        // data: { breadcrumb: 'Productos' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'productos-padre',
        loadChildren: () => import('../../modules/prod-padre/prod-padre.module').then(m => m.ProdPadreModule),
        data: { breadcrumb: 'Productos compuestos' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'familias',
        loadChildren: () => import('../../modules/familia/familia.module').then(m => m.FamiliaModule),
        data: { breadcrumb: 'Productos' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'personas',
        loadChildren: () => import('../../modules/persona/persona.module').then(m => m.PersonaModule),
        data: { breadcrumb: 'Productos' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'usuarios',
        loadChildren: () => import('../../modules/usuario/usuario.module').then(m => m.UsuarioModule),
        data: { breadcrumb: 'Usuarios' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'clientes',
        loadChildren: () => import('../../modules/cliente/cliente.module').then(m => m.ClienteModule),
        data: { breadcrumb: 'Usuarios' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'empresas',
        loadChildren: () => import('../../modules/empresa/empresa.module').then(m => m.EmpresaModule),
        data: { breadcrumb: 'empresas' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'proveedores',
        loadChildren: () => import('../../modules/proveedor/proveedor.module').then(m => m.ProveedorModule),
        data: { breadcrumb: 'proveedores' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'ventas',
        loadChildren: () => import('../../modules/venta/venta.module').then(m => m.VentaModule),
        data: { breadcrumb: 'Ventas' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'presupuestos',
        loadChildren: () => import('../../modules/presupuesto/presupuesto.module').then(m => m.PresupuestoModule),
        data: { breadcrumb: 'Presupuesto' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'cajas',
        loadChildren: () => import('../../modules/caja/caja.module').then(m => m.CajaModule),
        data: { breadcrumb: 'Proveedores' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'ofertas',
        loadChildren: () => import('../../modules/oferta/oferta.module').then(m => m.OfertaModule),
        data: { breadcrumb: 'ofertas' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'compras',
        loadChildren: () => import('../../modules/compra/compra.module').then(m => m.CompraModule),
        data: { breadcrumb: 'ofertas' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'config',
        loadChildren: () => import('../../modules/config/config.module').then(m => m.ConfigModule),
        data: { breadcrumb: 'ofertas' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'entidades',
        loadChildren: () => import('../../modules/entidad/entidad.module').then(m => m.EntidadModule),
        data: { breadcrumb: 'entidades' },
        canActivate: [AuthGuardService],
      },
      {
        path: 'informes',
        loadChildren: () => import('../../modules/informe/informe.module').then(m => m.InformeModule),
        data: { breadcrumb: 'informes' },
        canActivate: [AuthGuardService],
      },
      {
        path: '**',
        redirectTo: 'home'
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
