import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConfigComponent } from './config.component';
import { UpdateAppPage } from './pages/app/update-app.page';
import { UpdateConfigPage } from './pages/config/update-config.page';

const routes: Routes = [
  {
    path: '',
    component: ConfigComponent,
    children: [
      {
        path: 'app',
        component: UpdateAppPage,
        pathMatch: 'full'
      },
      {
        path: 'data',
        component: UpdateConfigPage,
        pathMatch: 'full'
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigRoutingModule { }
