import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth.service'
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { finalize, pluck, tap } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { Observable } from 'rxjs';

@Component({
  selector: 'recuperar-pass-save',
  templateUrl: './recuperar-pass-save.component.html'
})
export class RecuperarPassSaveComponent implements OnInit, AfterViewInit {

  public dataForm: IDataForm = { submitted: false };
  public formPass: FormGroup;
  public loading: boolean = false;
  public send: boolean = false;
  public token: string = null;
  public pass$: Observable<any>
  public counter: number = 5;

  constructor(private authService: AuthService, private router: Router,
    private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['home']);
    }
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.token = params.get("token");
        if (this.token) {
          this.pass$ = this.authService.validateToken(this.token).pipe(pluck('data'), tap(resp => {
            this.formPass.patchValue({ token: this.token });
          }))
        } else {
          this.router.navigate(['home']);
        }
      });
    this.formPass = this.formBuilder.group({
      token: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(6)]],
      $$password: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(6), RxwebValidators.compare({ fieldName: 'password' })]],
    }, { validator: this.passwordConfirming });

  }
  updatePass() {
    this.dataForm.submitted = true;
    if (this.formPass.valid) {
      this.dataForm.submitted = false;
      this.loading = true;
      this.authService.updatePass({ token: this.formPass.value.token, password: this.formPass.value.password }).pipe(finalize(() => {
        this.loading = false;
      })).subscribe(resp => {
        this.send = true;
        let intervalId = setInterval(() => {
          this.counter = this.counter - 1;
          this.cdr.markForCheck();
          if (this.counter === 0) {
            clearInterval(intervalId);
            this.router.navigate(['auth/login']);
          }
        }, 1000);
      }
      );
    }
  };
  passwordConfirming(c: AbstractControl): { invalid_password: boolean } {
    if (c.get('password').value !== c.get('$$password').value) {
      return { invalid_password: true };
    }
  }
  get f() {
    return this.formPass.controls;
  }
}
