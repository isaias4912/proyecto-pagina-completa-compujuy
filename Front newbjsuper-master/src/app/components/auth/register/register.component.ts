import { Component, OnInit, AfterViewInit, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { User } from '../../../core/models/user';
import { AuthService } from '../../../core/services/auth/auth.service'
import { Router } from '@angular/router';
import { map, filter, switchMap, distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Observable, Subscription, fromEvent } from 'rxjs';
import { UsuarioPublicHTTPService } from 'src/app/core/services/http/usuario-public-http.service';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageUserOkComponent } from '../message-user-ok/message-user-ok';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {
  @ViewChild("txtUserForm", { static: false })
  txtUser: ElementRef;
  @ViewChild("txtMailForm", { static: false })
  txtMail: ElementRef;
  public formRegister: FormGroup;
  public dataForm: IDataForm = { submitted: false };
  user = new User();
  public loading: boolean = false;
  public searchUser$: Observable<any>;
  public searchMail$: Observable<any>;
  public subUser: Subscription;
  public subMail: Subscription;
  public captchaKey = environment.captchaKey;

  constructor(private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder,
    private usuarioPublicHTTPService: UsuarioPublicHTTPService,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal
  ) {
    this.user.username = "admin"
    this.user.password = "654321"
    this.user.grant_type = "password"
    this.user.scope = "read"
  }
  ngOnInit(): void {
    this.formRegister = this.formBuilder.group({
      persona: this.formBuilder.group({
        nombre: [null, [Validators.required, Validators.maxLength(30), Validators.minLength(2)]],
        apellido: [null, [Validators.required, Validators.maxLength(20), Validators.minLength(2)]],
      }),
      mail: [null, [Validators.required, Validators.maxLength(50), Validators.email, Validators.minLength(2), this.validateExisteMail(), this.validateMail()]],
      captcha: [null, [Validators.required]],
      usuario: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(4), this.validateExisteUser(), this.validateUser()]],
      password: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(6)]],
      $$password: [null, [Validators.required, Validators.maxLength(15), Validators.minLength(6), RxwebValidators.compare({ fieldName: 'password' })]],
    }, { validator: this.passwordConfirming });
    // $('body').addClass('bg-deep-blue');
  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.searchUser$ = fromEvent(this.txtUser.nativeElement, 'keyup').pipe(
      map((event: any) => event.target.value), filter(key => (key != null && key != '' && key != undefined && key.length > 3 && key.length < 16)), distinctUntilChanged(), debounceTime(280),
      switchMap(search => this.usuarioPublicHTTPService.existUser(search)
      ));
    this.subUser = this.searchUser$.subscribe(search => {
      this.formRegister.controls['usuario']['existe_user'] = search.data.existe;
      this.formRegister.controls['usuario']['validate_user'] = true;
      this.formRegister.controls['usuario'].updateValueAndValidity();
      this.cdr.markForCheck();
    });
    this.searchMail$ = fromEvent(this.txtMail.nativeElement, 'keyup').pipe(
      map((event: any) => event.target.value), filter(key => (key != null && key != '' && key != undefined && key.length > 2 && key.length < 51)), distinctUntilChanged(), debounceTime(280),
      switchMap(search => this.usuarioPublicHTTPService.existMailUser(search)
      ));
    this.subMail = this.searchMail$.subscribe(search => {
      this.formRegister.controls['mail']['existe_mail'] = search.data.existe;
      this.formRegister.controls['mail']['validate_mail'] = true;
      this.formRegister.controls['mail'].updateValueAndValidity();
      this.cdr.markForCheck();
    });
    $(window).on('resize', () => {
      this.resizeClass();
    });
    this.resizeClass();
  }
  public resolved(captchaResponse: string) {
  }
  register() {
    this.dataForm.submitted = true;
    if (this.formRegister.valid) {
      this.loading = true;
      this.usuarioPublicHTTPService.saveUser(this.formRegister.value).subscribe(resp => {
        this.dataForm.submitted = false;
        this.loading = false;
        this.formRegister.reset();
        let modal = this.modalService.open(MessageUserOkComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      });
    }
  };
  initFocus() {
    setTimeout(() => {
      $("#nombre").focus();
    });
  }
  passwordConfirming(c: AbstractControl): { invalid_password: boolean } {
    if (c.get('password').value !== c.get('$$password').value) {
      return { invalid_password: true };
    }
  }
  validateExisteUser(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['existe_user']) {
        return { 'existe_user': true };
      }
      return null;
    };
  }
  validateUser(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['validate_user']) {
        return null;
      }
      return { 'validate_user': true };
    };
  }
  validateExisteMail(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['existe_mail']) {
        return { 'existe_mail': true };
      }
      return null;
    };
  }
  validateMail(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['validate_mail']) {
        return null;
      }
      return { 'validate_mail': true };
    };
  }
  resizeClass() {
    let win = document.body.clientWidth;
    if (win < 1250) {
      $('.app-container').addClass('closed-sidebar-mobile closed-sidebar');
    } else {
      $('.app-container').removeClass('closed-sidebar-mobile closed-sidebar');
    }
  }
  get f() {
    return this.formRegister.controls;
  }
  get fp() {
    let formPersona: any = this.formRegister.get('persona');
    return formPersona.controls;
  }
}
