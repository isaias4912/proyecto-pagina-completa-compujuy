import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FactCompraComponent } from './fact-comp.component'
import { NewFactCompPage } from './pages/new-fact-comp.page';
import { ListFactCompPage } from './pages/list-fact-comp.page';
const routes: Routes = [
  {
    path: '',
    component: FactCompraComponent,
    children: [
      {
        path: 'lista-compras',
        component: ListFactCompPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de compras',
          breadcrumb: [
            {
              label: 'Lista de compras',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva-compra',
        component: NewFactCompPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de compras',
              url: '/compras/lista'
            },
            {
              label: 'Nueva compra',
              url: null
            }
          ]
        }
      },
      {
        path: 'cta-cte',
        loadChildren: () => import('../../../../modules/cta-cte-prov/cta-cte-prov.module').then(m => m.CtaCteProvModule),
        data: { breadcrumb: 'Cta cte' },
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FactCompraRoutingModule { }
