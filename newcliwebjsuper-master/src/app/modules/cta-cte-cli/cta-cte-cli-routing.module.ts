import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CtaCteCliComponent } from './cta-cte-cli.component';
import { ListCtaCtePage } from './pages/list-cta-cte.page';

const routes: Routes = [
  {
    path: '',
    component: CtaCteCliComponent,
    children: [
      {
        path: 'movimientos',
        component: ListCtaCtePage,
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
export class CtaCteCliRoutingModule { }
