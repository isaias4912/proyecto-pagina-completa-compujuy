import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FamiliaComponent } from './familia.component';
import { ListFamPage } from './pages/list-fam.page';
import { NewFamPage } from './pages/new-fam.page';
import { UpdateFamPage } from './pages/update-fam.page';

const routes: Routes = [
  {
    path: '',
    component: FamiliaComponent,
    children: [
      {
        path: 'lista',
        component: ListFamPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de familias',
          breadcrumb: [
            {
              label: 'Lista de familias',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva',
        component: NewFamPage,
        pathMatch: 'full',
        data: {
          title: 'Nuevo producto padre',
          breadcrumb: [
            {
              label: 'Lista de familias',
              url: '/familias/lista'
            },
            {
              label: 'Nueva familia',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idFamilia',
        component: UpdateFamPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de familias',
              url: '/familias/lista'
            },
            {
              label: 'Editar familia - {{customText}}',
              url: ''
            }
          ]
        }
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FamiliaRoutingModule { }
