import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CtaCteProvComponent } from './cta-cte-prov.component';
import { ListCtaCteProvPage } from './pages/list-cta-cte-prov.page';

const routes: Routes = [
  {
    path: '',
    component: CtaCteProvComponent,
    children: [
      {
        path: 'movimientos',
        component: ListCtaCteProvPage,
        pathMatch: 'full'
      }
      // {
      //   path: 'nuevo',
      //   component: NewCliPage,
      //   pathMatch: 'full'
      // },
      // {
      //   path: 'modificar/:idCliente',
      //   component: UpdateCliPage,
      //   pathMatch: 'full'
      // },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CtaCteProvRoutingModule { }
