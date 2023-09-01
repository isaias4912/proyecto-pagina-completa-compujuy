import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from './core/services/auth/auth-guard.service';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AuthComponent } from './components/auth/auth.component';
import { RecuperarPassComponent } from './components/auth/recuperar-pass/recuperar-pass.component';
import { RecuperarPassSaveComponent } from './components/auth/recuperar-pass-save/recuperar-pass-save.component';


const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: 'recuperar-password',
        component: RecuperarPassComponent,
        data: {
          title: 'Recuperar contraseña',
        }
      },
      {
        path: 'recuperar-password/:token',
        component: RecuperarPassSaveComponent,
        data: {
          title: 'Restaurar contraseña',
        }
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
    ]
  },
  // {
  //   path: 'auth/login',
  //   component: LoginComponent,
  //   pathMatch: 'full',
  // },
  // {
  //   path: 'auth/register',
  //   component: RegisterComponent,
  //   pathMatch: 'full',
  // },
  {
    path: '',
    loadChildren: () => import('./modules/main/main.module').then(m => m.MainModule),
    canActivate: [AuthGuardService],
  },
  // {
  //   path: 'auth',
  //   loadChildren: () => import('./components/auth/auth.module').then(m => m.AuthModule),
  // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
