import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JwtModule } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppInjector } from './core/services/utils/app-injector';
import { CoreModule } from './core/core.module';
import { HttpIntercept } from './core/services/http/http-intercept';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { LoadingBarRouterModule } from '@ngx-loading-bar/router';
import { LoadingBarModule } from '@ngx-loading-bar/core';
import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { RecaptchaModule, RecaptchaFormsModule } from 'ng-recaptcha';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { EnterDirective } from './directives/enter.directive';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { MessageUserOkComponent } from './components/auth/message-user-ok/message-user-ok';
import { AuthComponent } from './components/auth/auth.component';
import { RecuperarPassComponent } from './components/auth/recuperar-pass/recuperar-pass.component';
import { RecuperarPassSaveComponent } from './components/auth/recuperar-pass-save/recuperar-pass-save.component';

export function tokenGetter() {
  return localStorage.getItem("access_token");
}
@NgModule({
  declarations: [
    AppComponent,
    EnterDirective,
    RegisterComponent,
    MessageUserOkComponent,
    LoginComponent,
    AuthComponent,
    RecuperarPassComponent,
    RecuperarPassSaveComponent
  ],
  imports: [
    NgbModule,
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ enableHtml: true }), // ToastrModule added
    LoadingBarRouterModule,
    LoadingBarModule,
    RouterModule,

    // ProductoModule,
    LoadingBarHttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: [environment.clientJWT],
        blacklistedRoutes: [environment.loginURL]
      }
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpIntercept,
      multi: true
    },
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  entryComponents: [
    MessageUserOkComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private injector: Injector) {    // Create global Service Injector.
    AppInjector.setInjector(injector);
  }
}
