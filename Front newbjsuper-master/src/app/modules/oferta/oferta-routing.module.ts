import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OfertaComponent } from './oferta.component';
import { ListOferPage } from './pages/list-ofer.page';
import { NewOferPage } from './pages/new-ofer.page';
import { AsignOferPage } from './pages/asign-ofer.page';
import { ListAsignPage } from './pages/list-asign.page';

const routes: Routes = [
  {
    path: '',
    component: OfertaComponent,
    children: [
      {
        path: 'lista',
        component: ListOferPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de ofertas',
          breadcrumb: [
            {
              label: 'Lista de ofertas',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva',
        component: NewOferPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de ofertas',
              url: '/ofertas/lista'
            },
            {
              label: 'Nueva oferta',
              url: null
            }
          ]
        }
      },
      {
        path: 'asignacion/:idOferta',
        component: AsignOferPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de ofertas',
              url: '/ofertas/lista'
            },
            {
              label: 'Asignar productos a oferta',
              url: null
            }
          ]
        }
      },
      {
        path: 'asignacion/:idOferta/lista',
        component: ListAsignPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de ofertas',
              url: '/ofertas/lista'
            },
            {
              label: 'Asignaciones de productos a oferta',
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
export class OfertaRoutingModule { }
