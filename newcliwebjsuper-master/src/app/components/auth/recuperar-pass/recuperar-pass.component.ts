import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth.service'
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'recuperar-pass',
  templateUrl: './recuperar-pass.component.html'
})
export class RecuperarPassComponent implements OnInit, AfterViewInit {

  public dataForm: IDataForm = { submitted: false };
  public formPass: FormGroup;
  public loading: boolean = false;
  public send: boolean = false;
  public captchaKey = environment.captchaKey;
  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['home']);
    }
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
    this.formPass = this.formBuilder.group({
      email: [null, [Validators.required,Validators.email, Validators.maxLength(80), Validators.minLength(4)]],
      captcha: [null, [Validators.required]]
    });

  }
  recuperarPass() {
    this.dataForm.submitted = true;
    if (this.formPass.valid) {
      this.dataForm.submitted = false;
      this.loading = true;
      this.authService.recuperarPass(this.formPass.value).pipe(finalize(() => {
        this.loading = false;
      })).subscribe(resp => {
        this.send=true;
      }
      );
    }
  };
  public resolved(captchaResponse: string) {
  }
  get f() {
    return this.formPass.controls;
  }
}
