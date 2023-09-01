import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProdPadreComponent } from './prod-padre.component';
import { NewProdPadrePage } from './pages/new-prod-padre.page';
import { UpdateProdPadrePage } from './pages/update-prod-padre.page';
import { ListProdPadreComponent } from './modules/shared-prod-padre/components/list/list-prod-padre.component';

const routes: Routes = [
  {
    path: '',
    component: ProdPadreComponent,
    children: [
      {
        path: 'lista',
        component: ListProdPadreComponent,
        pathMatch: 'full',
        data: {
          title: 'Lista de productos',
          breadcrumb: [
            {
              label: 'Lista de productos padre',
              url: ''
            }
          ]
        }
      },
      {
        path: 'nuevo',
        component: NewProdPadrePage,
        pathMatch: 'full',
        data: {
          title: 'Nuevo producto padre',
          breadcrumb: [
            {
              label: 'Lista de productos padre',
              url: '/productos-padre/lista'
            },
            {
              label: 'Nuevo producto padre',
              url: null
            }
          ]
        }
      },
      {
        path: 'modificar/:idProdPadre',
        component: UpdateProdPadrePage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos-padre/lista'
            },
            {
              label: 'Editar producto padre - {{customText}}',// pageTwoID Parameter value will be add 
              url: ''
            }
          ]
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdPadreRoutingModule { }
