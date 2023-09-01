import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompraComponent } from './compra.component'
import { FactCompraComponent } from './modules/factura-comp/fact-comp.component'
const routes: Routes = [
  {
    path: '',
    component: CompraComponent,
    children: [
      {
        path: 'facturas',
        loadChildren: () => import('../../modules/compra/modules/factura-comp/fact-comp.module').then(m => m.FactCompraModule),
        data: { breadcrumb: 'facturas' },
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompraRoutingModule { }
