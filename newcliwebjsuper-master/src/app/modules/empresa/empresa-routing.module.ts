import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmpresaComponent } from './empresa.component';
import { ListEmpPage } from './pages/list-emp.page';
import { NewEmpPage } from './pages/new-emp.page';
import { UpdateEmpPage } from './pages/update-emp.page';

const routes: Routes = [
  {
    path: '',
    component: EmpresaComponent,
    children: [
      {
        path: 'lista',
        component: ListEmpPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de empresas',
          breadcrumb: [
            {
              label: 'Lista de empresas',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva',
        component: NewEmpPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de empresas',
              url: '/empresas/lista'
            },
            {
              label: 'Nueva empresa',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idEmpresa',
        component: UpdateEmpPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de empresas',
              url: '/empresas/lista'
            },
            {
              label: 'Editar empresa',
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
export class EmpresaRoutingModule { }
