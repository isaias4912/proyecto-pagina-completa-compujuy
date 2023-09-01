import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListPresupuestoVenComponent } from './components/list/list-presupuesto-ven.component';
import { ListPresupuestoVenPage } from './pages/list-presupuesto-ven.page';
import { NewPresupuestoVenPage } from './pages/new-presupuesto-ven.page';
import { PresupuestoVenComponent } from './presupuesto-ven.component';

const routes: Routes = [
  {
    path: '',
    component: PresupuestoVenComponent,
    children: [
      {
        path: 'lista',
        component: ListPresupuestoVenPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de presupuestos',
          breadcrumb: [
            {
              label: 'Lista de presupuestos',
              url: ''
            }
          ], 
          help:{
            url:'https://www.newstock.compujuy.com.ar/soporte/gestion-ventas?view=article&id=25&catid=15'
          }
        }
      },
      {
        path: 'nuevo',
        component: NewPresupuestoVenPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de prespuestos',
              url: '/ventas/facturas/lista'
            },
            {
              label: 'Nuevo presupuesto',
              url: null
            }
          ]
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PresupuestoVenRoutingModule { }
