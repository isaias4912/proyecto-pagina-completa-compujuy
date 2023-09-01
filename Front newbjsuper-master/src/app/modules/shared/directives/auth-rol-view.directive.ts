import { Directive,  Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth/auth.service';
declare var $: any;

@Directive({
  selector: '[authRolView]'
})
export class AuthRolViewDirective {

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
    ) { }

    @Input() set authRolView(roles: Array<string>) {
      console.log('roles', roles)
      if (this.authService.ifExistRol(roles)) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
  }

}