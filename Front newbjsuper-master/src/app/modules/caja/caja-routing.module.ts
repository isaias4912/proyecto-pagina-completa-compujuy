import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CajaComponent } from './caja.component';
import { TransComponent } from './components/transaccion/trans.component';
import { ListTransComponent } from './components/transaccion/list/list-trans.component';
import { TransMovComponent } from './components/transaccion/movimiento/trans-mov.component';
import { ListTransMovComponent } from './components/transaccion/movimiento/list/list-trans-mov.component';
import { ListCajaPage } from './pages/list-caja.page';
import { NewCajaPage } from './pages/new-caja.page';
import { UpdateCajaPage } from './pages/update-caja.page';

const routes: Routes = [
  {
    path: '',
    component: CajaComponent,
    children: [
      {
        path: 'lista',
        component: ListCajaPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de cajas',
          breadcrumb: [
            {
              label: 'Lista de cajas',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva',
        component: NewCajaPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de cajas',
              url: '/cajas/lista'
            },
            {
              label: 'Nueva caja',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idCaja',
        component: UpdateCajaPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de cajas',
              url: '/cajas/lista'
            },
            {
              label: 'Editar caja',
              url: null
            }
          ]
        }
      },
      {
        path: 'transacciones',
        component: TransComponent,
        children: [
          {
            path: 'lista',
            component: ListTransComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: [
                {
                  label: 'Lista de transacciones',
                  url: ''
                }
              ]
            }
          },
          {
            path: 'movimientos',
            component: TransMovComponent,
            children: [
              {
                path: 'lista',
                component: ListTransMovComponent,
                pathMatch: 'full',
                data: {
                  breadcrumb: [
                    {
                      label: 'Lista de movimientos',
                      url: ''
                    }
                  ]
                }
              },
            ]
          },
        ]
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CajaRoutingModule { }
