import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClienteComponent } from './cliente.component';
import { ListCliPage } from './pages/list-cli.page';
import { NewCliPage } from './pages/new-cli.page';
import { UpdateCliPage } from './pages/update-cli.page';

const routes: Routes = [
  {
    path: '',
    component: ClienteComponent,
    children: [
      {
        path: 'lista',
        component: ListCliPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de clientes',
          breadcrumb: [
            {
              label: 'Lista de clientes',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nuevo',
        component: NewCliPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de clientes',
              url: '/clientes/lista'
            },
            {
              label: 'Nuevo cliente',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idCliente',
        component: UpdateCliPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de clientes',
              url: '/clientes/lista'
            },
            {
              label: 'Editar cliente',
              url: null
            }
          ]
        }
      },
      {
        path: 'cta-cte',
        loadChildren: () => import('../cta-cte-cli/cta-cte-cli.module').then(m => m.CtaCteCliModule),
        data: { breadcrumb: 'Cta cte' },
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClienteRoutingModule { }
