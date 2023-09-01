import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth.service'
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {

  public dataForm: IDataForm = { submitted: false };
  // user = new User();
  public formLogin: FormGroup;
  public loading: boolean = false;
  public typePass: boolean = false;
  public captchaKey = environment.captchaKey;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
    // this.user.username = "admin"
    // this.user.password = "654321"
    // this.user.grant_type = "password"
    // this.user.scope = "read"
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['home']);
    }
  }
  ngAfterViewInit(): void {
    $(window).on('resize', () => {
      this.resizeClass();
    });
    this.resizeClass();
  }
  ngOnInit(): void {
    this.formLogin = this.formBuilder.group({
      username: [null, [Validators.required, Validators.maxLength(50), Validators.minLength(2)]],
      password: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(6)]],
      grant_type: ['password', []],
      captcha: [null, [Validators.required]],
      scope: ['read', []]
    });
    // $('body').addClass('bg-deep-blue');
    this.authService.clear();

  }
  login() {
    this.dataForm.submitted = true;
    if (this.formLogin.valid) {
      this.dataForm.submitted = false;
      this.loading = true;
      this.authService.login(this.formLogin.value).pipe(finalize(() => {
        this.loading = false;
      })).subscribe(resp => {
        // $('body').removeClass('bg-arielle-smile');
        let tempRoute = localStorage.getItem('last_url_jsuper');
        if (tempRoute) {
          try {
            localStorage.removeItem('last_url_jsuper');
            this.router.navigate([tempRoute])
          } catch (e) {
            this.router.navigate(['home'])
          }
        } else {
          this.router.navigate(['home'])
        }
      }
      );
    }
  };
  resizeClass() {
    let win = document.body.clientWidth;
    if (win < 1250) {
      $('.app-container').addClass('closed-sidebar-mobile closed-sidebar');
    } else {
      $('.app-container').removeClass('closed-sidebar-mobile closed-sidebar');
    }
  }
  public resolved(captchaResponse: string) {
  }
  get f() {
    return this.formLogin.controls;
  }
}
