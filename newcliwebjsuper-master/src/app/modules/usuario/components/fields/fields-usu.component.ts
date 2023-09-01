import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef, OnDestroy, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl, AbstractControl } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { Fields } from '../../../../core/fields';
import { Usuario } from '../../../../core/models/usuario';
import { UsuarioHTTPService } from '../../../../core/services/http/usuario-http.service';
import { environment } from '../../../../../environments/environment'
import { Imagen } from 'src/app/core/models/imagen';
import { Observable, Subscription } from 'rxjs';
import { pluck, tap } from 'rxjs/operators';
import { SelectDualListComponent } from 'src/app/modules/shared/components/sucursal/select-dual-list/select-dual-list.component';
import { SelectPerComponent } from 'src/app/modules/persona/components/select/select-per.component';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { PathService } from 'src/app/core/services/utils/path.service';
import { ImagePreviewComponent } from 'src/app/modules/shared/components/image-preview/image-preview.component';

declare var $: any;

@Component({
  selector: 'fields-usu',
  templateUrl: './fields-usu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsUsuComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public type: number = 2; // 1 : my 2 : users
  @Input()
  public formUsuario: FormGroup;
  @Input()
  public usuario: Usuario;
  @ViewChild(ImagePreviewComponent, { static: false })
  imagePreviewComponent: ImagePreviewComponent;
  @ViewChild(SelectDualListComponent, { static: false })
  selectDualListComponent: SelectDualListComponent;
  @ViewChild(SelectPerComponent, { static: false })
  selectPerComponent: SelectPerComponent;
  public baseURLImg: string = environment.baseURLImgUsers;
  public imagen: Imagen;
  public roles$: Observable<any>;
  public dataImage: any;
  private subRoles: Subscription;
  public pathImage: string = "/images/users/";
  public pathImageUpload: string = "usuarios/upload/image";
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private usuarioHTTPService: UsuarioHTTPService,
    private authService: AuthService,
    public pathService: PathService
  ) {
    super();
  }

  ngOnInit() {
    if (this.type == 1) {
      this.pathImage = '/images/my/';
      this.pathImageUpload = 'usuarios/my/upload/image';
    }
    this.formUsuario = this.formBuilder.group({
      usuario: [this.usuario ? this.usuario.usuario : null, [Validators.required]],
      password: [this.usuario ? this.usuario.password : null, [Validators.required]],
      $$password2: [this.usuario ? this.usuario.password : null, [Validators.required]],
      $$updatePassword: [false, []],
      mail: [this.usuario ? this.usuario.mail : null, [Validators.email, Validators.required]],
      logo: [this.usuario ? this.usuario.logo : null, []],
      altLogo: [this.usuario ? this.usuario.altLogo : null, []],
      tipoLogo: [this.usuario ? this.usuario.tipoLogo : null, []],
      persona: [this.usuario ? this.usuario.persona : null, [Validators.required]],
      roles: new FormArray([]),
      // sucursales: new FormControl([])
      sucursales: [this.usuario ? this.usuario.sucursales : null, []],
    }, { validator: this.passwordConfirming });

    this.roles$ = this.usuarioHTTPService.getRoles().pipe(pluck('data'), tap(resp => {
      if (this.usuario) {
        this.addRol(resp.find(x => x.id == this.usuario.roles[0].id));
      } else {
        this.addRol(null)
      }
    }));
    if (this.usuario) {
      this.formUsuario.get('password').clearValidators();
      this.formUsuario.get('$$password2').clearValidators();
      this.formUsuario.updateValueAndValidity();
      if (this.usuario.tipoLogo && this.usuario.tipoLogo != '0') {
        this.imagen = new Imagen();
        this.imagen.tipo = this.usuario.tipoLogo;
        this.imagen.alt = this.usuario.altLogo;
        this.imagen.nombre = this.usuario.logo;
        this.imagen.tamanio = null;
      } else {
        this.imagen = new Imagen();
        this.imagen.tipo = '0';
        this.imagen.alt = 'Imagen de keygravatar generada con el email';
      }
    }
  }
  addRol(rol) {
    let f: FormGroup = this.formBuilder.group({
      id: [rol ? rol.id : null, [Validators.required]],
      nombre: [rol ? rol.nombre : null, [Validators.required]],
      alias: [rol ? rol.alias : null, []],
      $$rol: [rol ? rol : null, [Validators.required]],
    });
    this.fr.push(f);
    this.subRoles = f.get('$$rol').valueChanges.subscribe(val => {
      f.patchValue({ id: val ? val.id : null });
      f.patchValue({ nombre: val ? val.nombre : null });
    });
  }
  passwordConfirming(c: AbstractControl): { invalid_password: boolean } {
    if (c.get('password').value !== c.get('$$password2').value) {
      return { invalid_password: true };
    }
  }
  changueUpdatePassword = function () {
    this.formUsuario.get('password').clearValidators();
    this.formUsuario.get('$$password2').clearValidators();
    if (this.f.$$updatePassword.value) {
      this.formUsuario.get('password').setValidators([Validators.required]);
      this.formUsuario.get('$$password2').setValidators([Validators.required]);
      setTimeout(() => {
        $("#txtUsuarioPassword1").focus();
      });
    }
    this.formUsuario.updateValueAndValidity();
  };
  ngAfterViewInit(): void {
    this.initFocus();
  }
  reset() {
    this.formUsuario.patchValue({
      usuario: null,
      password: null,
      $$password2: null,
      $$updatePassword: false,
      mail: null,
      logo: null,
      altLogo: null,
      tipoLogo: null,
      persona: null,
      roles: [],
      sucursales: new FormControl([])
    });
    this.selectDualListComponent.reset();
    this.selectPerComponent.resetData();
    this.dataForm = { ...this.dataForm, submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtUsuarioUser").focus();
    });
  }
  get f() {
    return this.formUsuario.controls;
  }
  get fr() { return this.f.roles as FormArray; }

  isShow(): boolean {
    if (this.usuario) {
      let userLogged = this.authService.getUserLogged();
      return userLogged.username != this.usuario.usuario;
    }
    return true;
  }

  loadImage(resp) {
    if (resp.target == "addUsuario") {
      this.formUsuario.patchValue({ logo: resp.data[0].fileName, altLogo: resp.data[0].fileAlt, tipoLogo: 1 });
      this.formUsuario.updateValueAndValidity();
      this.imagen = new Imagen();
      this.imagen.tipo = resp.data[0].fileType;
      this.imagen.alt = resp.data[0].fileAlt;
      this.imagen.nombre = resp.data[0].fileName;
      this.imagen.tamanio = resp.data[0].fileSize;
      this.imagen.orden = resp.data[0].orden;
      this.imagePreviewComponent.loadImage(this.imagen);
      this.cdr.detectChanges();
    }
  }
  ngOnDestroy(): void {
    if (this.subRoles) {
      this.subRoles.unsubscribe();
    }
  }
  isAdminFromUser(user) {
    let resp = false;
    if (user.roles) {
      user.roles.forEach((rol) => {
        if (rol.nombre == "ROLE_ADMIN") {
          resp = true;
        }
      });
    };
    return resp;
  }
}

