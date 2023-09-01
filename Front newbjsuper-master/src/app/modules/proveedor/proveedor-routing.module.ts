import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProveedorComponent } from './proveedor.component';
import { ListProvPage } from './pages/list-prov.page';
import { NewProvPage } from './pages/new-prov.page';
import { UpdateProvPage } from './pages/update-prov.page';

const routes: Routes = [
  {
    path: '',
    component: ProveedorComponent,
    children: [
      {
        path: 'lista',
        component: ListProvPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de proveedores',
          breadcrumb: [
            {
              label: 'Lista de proveedores',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nuevo',
        component: NewProvPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de proveedores',
              url: '/proveedores/lista'
            },
            {
              label: 'Nuevo proveedor',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idProveedor',
        component: UpdateProvPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de proveedores',
              url: '/proveedores/lista'
            },
            {
              label: 'Editar proveedor',
              url: null
            }
          ]
        }
      },
      {
        path: 'cta-cte',
        loadChildren: () => import('../../modules/cta-cte-prov/cta-cte-prov.module').then(m => m.CtaCteProvModule),
        data: { breadcrumb: 'Cta cte' },
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProveedorRoutingModule { }
