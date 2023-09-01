import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PersonaComponent } from './persona.component';
import { ListPerPage } from './pages/list-per.page';
import { NewPerPage } from './pages/new-per.page';
import { UpdatePerPage } from './pages/update-per.page';

const routes: Routes = [
  {
    path: '',
    component: PersonaComponent,
    children: [
      {
        path: 'lista',
        component: ListPerPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de personas',
          breadcrumb: [
            {
              label: 'Lista de personas',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nueva',
        component: NewPerPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de personas',
              url: '/personas/lista'
            },
            {
              label: 'Nueva persona',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idPersona',
        component: UpdatePerPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de personas',
              url: '/personas/lista'
            },
            {
              label: 'Editar persona',
              url: null
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
export class PersonaRoutingModule { }
