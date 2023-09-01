import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InformeComponent } from './informe.component';
import { LibroIvaVenComponent } from './ventas/libro/libro-iva-ven.component';
import { LibroIvaCompComponent } from './compras/libro/libro-iva-comp.component';

const routes: Routes = [
  {
    path: '',
    component: InformeComponent,
    children: [
      {
        path: 'ventas/libro-iva',
        component: LibroIvaVenComponent,
        pathMatch: 'full',
        data: {
          title: 'Libro de iva de ventas',
          breadcrumb: [
            {
              label: 'Lista de IVA de ventas',
              url: ''
            }
          ]
        }
      },
      {
        path: 'compras/libro-iva',
        component: LibroIvaCompComponent,
        pathMatch: 'full',
        data: {
          title: 'Libro de iva de compras',
          breadcrumb: [
            {
              label: 'Lista de IVA de compras',
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
export class InformeRoutingModule { }
