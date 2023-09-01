import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioComponent } from './usuario.component';
import { ListUsuPage } from './pages/list-usu.page';
import { NewUsuPage } from './pages/new-usu.page';
import { UpdateUsuPage } from './pages/update-usu.page';
import { MyUsuComponent } from './components/my/my-usu.component';

const routes: Routes = [
  {
    path: '',
    component: UsuarioComponent,
    children: [
      {
        path: 'lista',
        component: ListUsuPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de usuarios',
          breadcrumb: [
            {
              label: 'Lista de usuarios',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nuevo',
        component: NewUsuPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de usuarios',
              url: '/usuarios/lista'
            },
            {
              label: 'Nuevo usuario',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idUsuario',
        component: UpdateUsuPage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de usuarios',
              url: '/usuarios/lista'
            },
            {
              label: 'Editar usuario',
              url: null
            }
          ]
        }
      },
      {
        path: 'my',
        component: MyUsuComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Perfil',
              url: '/usuarios/my'
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
export class UsuarioRoutingModule { }
