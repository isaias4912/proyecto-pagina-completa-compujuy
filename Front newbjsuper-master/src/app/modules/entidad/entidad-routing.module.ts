import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EntidadComponent } from './entidad.component';
import { ListEntPage } from './pages/list-ent.page';


const routes: Routes = [
  {
    path: '',
    component: EntidadComponent,
    children: [
      {
        path: 'lista',
        component: ListEntPage,
        pathMatch: 'full'
      },
     
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EntidadRoutingModule { }
