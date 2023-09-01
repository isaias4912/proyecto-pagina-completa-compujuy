import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FactVenComponent } from './fact-ven.component';
import { ListFactVenPage } from './pages/list-fact-ven.page';
import { NewFactVenPage } from './pages/new-fact-ven.page';

const routes: Routes = [
  {
    path: '',
    component: FactVenComponent,
    children: [
      {
        path: 'lista',
        component: ListFactVenPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de ventas',
          breadcrumb: [
            {
              label: 'Lista de ventas',
              url: ''
            }
          ], 
          help:{
            url:'https://www.newstock.compujuy.com.ar/soporte/gestion-ventas?view=article&id=25&catid=15'
          }
        }
      },
      {
        path: 'nueva',
        component: NewFactVenPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de ventas',
              url: '/ventas/facturas/lista'
            },
            {
              label: 'Nueva venta',
              url: null
            }
          ]
        }
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FactVenRoutingModule { }
