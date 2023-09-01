import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VentaComponent } from './venta.component';

const routes: Routes = [
  {
    path: '',
    component: VentaComponent,
    children: [
      {
        path: 'facturas',
        loadChildren: () => import('../../modules/venta/modules/factura-ven/fact-ven.module').then(m => m.FactVenModule),
        data: { breadcrumb: 'facturas' },
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VentaRoutingModule { }
