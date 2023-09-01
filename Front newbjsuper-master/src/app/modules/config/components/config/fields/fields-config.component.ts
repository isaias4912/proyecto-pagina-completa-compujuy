import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataForm } from '../../../../../core/models/idata-form';
import { Fields } from '../../../../../core/fields';
import { environment } from '../../../../../../environments/environment'
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
import { ConfigHTTPService } from 'src/app/core/services/http/config-http.service';
import { Imagen } from 'src/app/core/models/imagen';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { pluck, catchError } from 'rxjs/operators';
import * as download from 'downloadjs';
import { throwError } from 'rxjs';
import * as Quill from 'quill'
import ImageResize from 'quill-image-resize-module';
import { PathService } from 'src/app/core/services/utils/path.service';
import { ImagePreviewComponent } from 'src/app/modules/shared/components/image-preview/image-preview.component';
Quill.register('modules/imageResize', ImageResize);
declare var $: any;

@Component({
  selector: 'fields-config',
  templateUrl: './fields-config.component.html',
  styleUrls: ['./fields-config.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsConfigComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  public dataFormAfipCSR: IDataForm = { submitted: false };
  @Input()
  public formConfig: FormGroup;
  public formConfigAfipCSR: FormGroup;
  @Input()
  public config: any;
  @ViewChild('fechaFecIniActForm', { static: false })
  fechaFecIniAct: DateComponent;
  public baseURLImg: string = environment.baseURLImgConfig;
  public imagen: Imagen;
  @ViewChild(ImagePreviewComponent, { static: false })
  imagePreviewComponent: ImagePreviewComponent;
  public editor_modules = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
      ['blockquote'],
      [{ 'list': 'ordered' }, { 'list': 'bullet' }],
      [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
      [{ 'direction': 'rtl' }],                         // text direction

      [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      ['link', 'image', 'video'],          // add's image support
      [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
      [{ 'font': [] }],
      [{ 'align': [] }],
    ],
    imageResize: true,
  };
  // public Editor = Editor;
  public options: Object = {
    zIndex: 9999999,
    language: 'es',
    events: {
      'image.beforeUpload': function (files) {
        let editor: any = this;
        if (files.length) {
          // Create a File Reader.
          var reader = new FileReader();
          // Set the reader to insert images when they are loaded.
          reader.onload = (e: any) => {
            var result = e.target.result;
            editor.image.insert(result, null, null, editor.image.get());
          };
          // Read image as base64.
          reader.readAsDataURL(files[0]);
        }
        editor.popups.hideAll();
        // Stop default upload chain.
        return false;
      }
    }
  };
  public dataConfig = {
    paises: [{ id: 1, nombre: "ARGENTINA" }],
    condiciones: [
      { id: 'MONOTRIBUTO', name: "Monotributo" },
      { id: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { id: 'EXCENTO', name: "Exento" }
    ],
    ivas: [
      { idIva: 3, descripcion: "0%", valor: 0 },
      { idIva: 4, descripcion: "10.5%", valor: 10.5 },
      { idIva: 5, descripcion: "21.0%", valor: 21.0 },
      { idIva: 6, descripcion: "27.0%", valor: 27.0 },
      { idIva: 8, descripcion: "5.0%", valor: 5.0 },
      { idIva: 9, descripcion: "2.5%", valor: 2.5 }
    ]
  };

  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private configHTTPService: ConfigHTTPService,
    public pathService: PathService
  ) {
    super();
  }

  ngOnInit() {
    this.formConfig = this.formBuilder.group({
      id: [this.config ? this.config.id : null, [Validators.required]],
      cuitEmpresa: [this.config ? this.config.cuitEmpresa : null, [Validators.required, Validators.maxLength(11), Validators.minLength(10)]],
      razonSocial: [this.config ? this.config.razonSocial : null, [Validators.required, Validators.maxLength(200)]],
      domicilioComercial: [this.config ? this.config.domicilioComercial : null, [Validators.required, Validators.maxLength(100)]],
      printHost: [this.config ? this.config.printHost : null, [Validators.maxLength(60), Validators.minLength(2)]],
      printPort: [this.config ? this.config.printPort : null, [Validators.max(99999)]],
      tipoEmpresa: [this.config ? this.config.tipoEmpresa : null, [Validators.required]],
      fechaIniAct: [this.config ? this.config.fechaIniAct : null, [Validators.required]],
      ingresosBrutos: [this.config ? this.config.ingresosBrutos : null, [Validators.required, Validators.maxLength(50)]],
      pais: [this.config ? this.config.pais : null, [Validators.required]],
      iva: [this.config ? this.config.iva : null, [Validators.required]],
      margenGanancia: [this.config ? this.config.margenGanancia : null, [Validators.required, Validators.min(1), Validators.max(500)]],
      cliCtaCteInteres: [this.config ? this.config.cliCtaCteInteres : null, [Validators.required, Validators.min(0), Validators.max(500)]],
      afipProduccion: [this.config ? this.config.afipProduccion : false, [Validators.required]],
      privateKey: [this.config ? this.config.privateKey : null, []],
      // afipProduccion: [this.config ? this.config.afipProduccion : false, [Validators.required]],
      // privateKey: [this.config ? this.config.privateKey : null, []],
      // certO: [this.config ? this.config.certO : null, [Validators.maxLength(50)]],
      // certCN: [this.config ? this.config.certCN : null, [Validators.maxLength(50)]],
      // certSerialNumber: [this.config ? this.config.certSerialNumber : null, []],
      // certPassword: [this.config ? this.config.certPassword : null, [Validators.maxLength(50)]],
      logoReporte: [this.config ? this.config.logoReporte : null, []],
      encabezadoReporte: [this.config ? this.config.encabezadoReporte : null, []],
      puntosVenta: new FormArray([])
    });
    this.formConfigAfipCSR = this.formBuilder.group({
      certO: [this.config ? this.config.certO : null, [Validators.required, Validators.maxLength(20)]],
      certCN: [this.config ? this.config.certCN : null, [Validators.required, Validators.maxLength(20)]],
      certSerialNumber: [this.config ? this.config.certSerialNumber : null, [Validators.required]],
      certPassword: [this.config ? this.config.certPassword : null, [Validators.required, Validators.maxLength(10)]],
    });

    if (this.config.logoReporte && this.config.logoReporte != '') {
      this.imagen = new Imagen();
      this.imagen.alt = 'Logo para reportes';
      this.imagen.nombre = this.config.logoReporte;
      this.imagen.tipo = '1';
    }
    if (this.config && this.config.puntosVenta && this.config.puntosVenta.length > 0) {
      this.config.puntosVenta.forEach(p => {
        this.addPtoVenta(p);
      });
    } else {
      this.addPtoVenta();
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
    if (this.config && this.config.fechaIniAct) {
      this.fechaFecIniAct.setDate(this.config.fechaIniAct);
    } else {
      this.fechaFecIniAct.setDate(null);
    }
    this.initFocus();
    this.loadHelp();
  }
  addPtoVenta(ptoVenta: any = null) {
    let nro = null;
    if (!this.fpv.controls.length || this.fpv.controls.length <= 0) {
      nro = 1
    }
    this.dataForm.submitted = true;
    if (this.formConfig.controls.puntosVenta.valid) {
      let f: FormGroup = this.formBuilder.group({
        nro: [ptoVenta ? ptoVenta.nro : nro, [Validators.required, RxwebValidators.unique()]],
        descripcion: [ptoVenta ? ptoVenta.descripcion : null, [Validators.required, Validators.maxLength(80)]],
      });
      this.fpv.push(f);
      this.dataForm.submitted = false;
      if (!ptoVenta) {
        setTimeout(() => {
          $("#txtNroPuntoVenta" + (this.fpv.length - 1)).focus();
        });
      }
    }
  }
  removePtoVenta(formSelected: FormGroup) {
    this.fpv.removeAt(this.fpv.controls.indexOf(formSelected));
    this.formConfig.updateValueAndValidity();
  }
  loadFile(resp) {
    if (resp.target == "addFileCRT") {

    }
  }
  loadImage(resp) {
    if (resp.target == "addImageConfig") {
      this.formConfig.patchValue({ logoReporte: resp.data[0].fileName, altLogo: resp.data[0].fileAlt, tipoLogo: 1 });
      this.formConfig.updateValueAndValidity();
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
  setConfigAfip() {
    this.formConfig.patchValue({
      afipProduccion: this.config.afipProduccion,
      privateKey: this.config.privateKey,
    });
    this.formConfigAfipCSR.patchValue({
      certO: this.config.certO,
      certCN: this.config.certCN,
      certSerialNumber: this.config.certSerialNumber,
      certPassword: this.config.certPassword,
    });
  }
  refreshDataAfip() {
    this.configHTTPService.getConfiguracion().pipe(pluck('data')).subscribe(resp => {
      this.config = resp;
      this.setConfigAfip();
    });
  }
  enableProduccionAfip() {
    let valueSet = this.formConfig.get('afipProduccion').value;
    this.configHTTPService.enableProduccionAfip(this.formConfig.get('afipProduccion').value ? 1 : 0).pipe(catchError((error) => {
      this.formConfig.patchValue({ afipProduccion: !valueSet });
      this.config.afipProduccion = !valueSet;
      return throwError(error);
    })).subscribe((config) => {
      this.messageToast.success('Se habilito el modo producción correctamente, pude realizar transacciones validas contra la AFIP, para corroborar funcionamiento realice el test en esta misma vista.');
      this.config.afipProduccion = valueSet;
      this.cdr.markForCheck();
    });
  };
  generateCSR() {
    this.dataFormAfipCSR.submitted = true;
    if (this.formConfigAfipCSR.valid) {
      this.configHTTPService.generateCSR(this.formConfigAfipCSR.value).subscribe(resp => {
        this.refreshDataAfip();
        this.messageToast.success('Se modificarón los datos de configuración del CSR y se genero correctamente');
      });
    }
  }
  generatePrivateKey() {
    this.configHTTPService.generatePrivateKey().subscribe(resp => {
      this.refreshDataAfip();
      this.messageToast.success('Nueva clave generada correctamente.');
    });
  }
  downloadCSR() {
    this.configHTTPService.downloadCSR().subscribe((resp: any) => {
      let blob = new Blob([resp], { type: "application/octet-stream" });
      download(blob, 'certificateCSR', "file/txt");
    });
  }
  downloadCRT() {
    this.configHTTPService.downloadCRT().subscribe((resp: any) => {
      let blob = new Blob([resp], { type: "application/octet-stream" });
      download(blob, this.config.certNameCRT + '.crt', "file/txt");
    });
  }
  testAfip() {
    this.configHTTPService.testAfip().subscribe((resp: any) => {
      this.messageToast.success('Test exitoso, puede habilitar el modo produccion para realizar transacciones validas contra la AFIP');
    });
  }
  reset() {
    this.formConfig.patchValue({
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtCUIT").focus();
    });
  }
  get f() {
    return this.formConfig.controls;
  }
  get fcacsr() {
    return this.formConfigAfipCSR.controls;
  }
  get fpv() { return this.f.puntosVenta as FormArray; }

  get fs() {
    return this.formConfig.controls.sucursalPrincipal['controls'];
  }
}

