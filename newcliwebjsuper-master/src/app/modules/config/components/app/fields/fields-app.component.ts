import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataForm } from '../../../../../core/models/idata-form';
import { Fields } from '../../../../../core/fields';
import { environment } from '../../../../../../environments/environment'
import { Imagen } from 'src/app/core/models/imagen';
import { PathService } from 'src/app/core/services/utils/path.service';
import { ImagePreviewComponent } from 'src/app/modules/shared/components/image-preview/image-preview.component';

declare var $: any;

@Component({
  selector: 'fields-app',
  templateUrl: './fields-app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsAppComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formApp: FormGroup;
  @Input()
  public app: any;
  public baseURLImg: string = environment.baseURLImgApp;
  public imagen: Imagen;
  public dataImage: any;
  @ViewChild(ImagePreviewComponent, { static: false })
  imagePreviewComponent: ImagePreviewComponent;
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    public pathService: PathService
  ) {
    super();
  }

  ngOnInit() {
    this.formApp = this.formBuilder.group({
      nombre: [this.app ? this.app.nombre : null, [Validators.required, Validators.maxLength(80), Validators.minLength(4)]],
      descripcion: [this.app ? this.app.descripcion : null, [Validators.required]],
      logo: [this.app ? this.app.logo : null, []],
      altLogo: [this.app ? this.app.altLogo : null, []],
      sucursalPrincipal: this.formBuilder.group({
        id: [this.app ? this.app.sucursalPrincipal.id : null, []],
        nombre: [this.app ? this.app.sucursalPrincipal.nombre : null, [Validators.required, Validators.maxLength(80), Validators.minLength(4)]],
        direccion: [this.app ? this.app.sucursalPrincipal.direccion : null, [Validators.required, Validators.maxLength(100), Validators.minLength(4)]],
        prefijoTelCel: [this.app ? this.app.sucursalPrincipal.prefijoTelCel : null, [Validators.required]],
        numeroTelCel: [this.app ? this.app.sucursalPrincipal.numeroTelCel : null, [Validators.required]],
        prefijoTelFijo: [this.app ? this.app.sucursalPrincipal.prefijoTelFijo : null, [Validators.required]],
        numeroTelFijo: [this.app ? this.app.sucursalPrincipal.numeroTelFijo : null, [Validators.required]],
        principal: [this.app ? this.app.sucursalPrincipal.principal : null, []],
        detalle: [this.app ? this.app.sucursalPrincipal.detalle : null, []]
      }),
    });
    if (this.app.logo && this.app.logo != '') {
      this.imagen = new Imagen();
      this.imagen.alt = this.app.altLogo;
      this.imagen.nombre = this.app.logo;
      this.imagen.tipo = '1';
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  loadImage(resp) {
    if (resp.target == "addImageApp") {
      this.formApp.patchValue({ logo: resp.data[0].fileName, altLogo: resp.data[0].fileAlt, tipoLogo: 1 });
      this.formApp.updateValueAndValidity();
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
  reset() {
    this.formApp.patchValue({
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtNombreNegEmp").focus();
    });
  }
  get f() {
    return this.formApp.controls;
  }
  get fs() {
    return this.formApp.controls.sucursalPrincipal['controls'];
  }
}

