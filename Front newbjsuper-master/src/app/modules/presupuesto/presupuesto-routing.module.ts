import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PresupuestoComponent } from './presupuesto.component';

const routes: Routes = [
  {
    path: '',
    component: PresupuestoComponent,
    children: [
      {
        path: 'cbtes',
        loadChildren: () => import('../presupuesto/modules/presupuesto-ven/presupuesto-ven.module').then(m => m.PresupuestoVenModule),
        data: { breadcrumb: 'facturas' },
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PresupuestoRoutingModule { }
