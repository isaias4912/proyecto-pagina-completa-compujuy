import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter, Renderer2, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators, AbstractControl, ValidatorFn } from '@angular/forms';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataForm } from 'src/app/core/models/idata-form';
// import * as Editor from 'src/ckeditor5/ckeditor'; // segun el tuto hay que ponerlo en src

// import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
declare var $: any;
declare var JsBarcode: any;
import * as moment from 'moment';
import * as download from 'downloadjs';
import * as Quill from 'quill'
import ImageResize from 'quill-image-resize-module';
Quill.register('modules/imageResize', ImageResize);

@Component({
  selector: 'gen-barcode',
  templateUrl: './gen-barcode.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenerateBarcodeComponent extends ComponentPage implements OnInit, AfterViewInit {

  public formItems: FormGroup;
  public dataForm: IDataForm;
  public dataPrint: any;
  public editor_modules: any={
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
    ]
    ,
    imageResize: true
  };
  // public Editor = Editor;
  @ViewChild('pdfBarcodeForm', { static: false }) pdfBarcode: ElementRef;
  public dataBarCode = {
    cols: 5,
    cantidad: false,
    codigo: false,
    codigoEspecial: false,
    textos: [{ id: 1, value: "Texto normal" }, { id: 2, value: "Texto enriquecido" }],
    codigos: [{ id: "ean13", value: "EAN13" }, { id: "code128", value: "CODE128" }],
    texto: null,
    showIframe: false,
    showTipoBarCode: true,
    tipoBarCode: "default"
  };

  constructor(
    private formBuilder: FormBuilder,
    private productosHTTPService: ProductosHTTPService,
    private renderer: Renderer2,
    private cdr: ChangeDetectorRef,
  ) {
    super();
  }

  ngOnInit() {
    this.dataBarCode.texto = this.dataBarCode.textos[0];
    this.dataBarCode.tipoBarCode = this.dataBarCode.codigos[0].id;
    this.formItems = this.formBuilder.group({
      items: new FormArray([]),
    });
    this.dataForm = { submitted: false };
    this.addItem();
  }
  addItem() {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [0, [Validators.required]],
        $$producto: [null, [Validators.required]],
        $$codigos: [null, [Validators.required]],
        $$precios: [null, [Validators.required]],
        descripcion: ["", []],
        descripcionNormal: ["", [Validators.required]],
        descripcionHTML: ["", [Validators.required]],
        codigo: [null, [Validators.required, , this.validateBarCode(this.dataBarCode)]],
        precio: [null, [Validators.required]],
        cantidad: [1, [Validators.required]],
      });
      this.fi.push(f);
      f.get('$$producto').valueChanges.subscribe(resp => {
        if (resp) {
          f.patchValue({
            id: resp.id,
            descripcion: resp.nombreFinal,
            $$codigos: resp.codigoBarras,
            $$precios: resp.precios
          });
          if (resp.codigoBarras != null && resp.codigoBarras.length > 0) {
            f.patchValue({ codigo: resp.codigoBarras[0].codigo });
          }
          if (resp.precios != null && resp.precios.length > 0) {
            f.patchValue({ precio: resp.precios[0].precio });
          }
        } else {
          f.patchValue({
            id: 0,
            $$codigos: null,
            $$precios: null,
            descripcion: "",
            codigo: null,
            precio: null,
            cantidad: 1,
          });
        }
        this.setDescripcion(f);
      });
      setTimeout(() => {
        $("#txtProducto" + (this.fi.controls.length - 1)).focus();
      });
    }
  }
  changeDescripcion() {
    this.fi.controls.forEach((f: FormGroup) => {
      this.setDescripcion(f);
    });
  }
  changeTipo() {
    this.fi.controls.forEach((f: FormGroup) => {
      f.get('codigo').updateValueAndValidity();
    });
  }
  setDescripcion(f: FormGroup) {
    if (this.fi.controls.length > 0) {
      if (f.get('$$producto').value) {
        f.patchValue({ descripcionNormal: f.get('$$producto').value.nombreFinal });
        f.patchValue({ descripcionHTML: f.get('$$producto').value.nombreFinal });
        if (this.dataBarCode.cantidad) {
          f.patchValue({ descripcionNormal: f.get('descripcionNormal').value + "  x " + f.get('$$producto').value.contenidoNeto + " " + f.get('$$producto').value.unidad.nombreCorto });
          f.patchValue({ descripcionHTML: f.get('descripcionHTML').value + "  x " + f.get('$$producto').value.contenidoNeto + " " + f.get('$$producto').value.unidad.nombreCorto });
        }
        if (this.dataBarCode.codigo) {
          f.patchValue({ descripcionNormal: f.get('descripcionNormal').value + "  - " + f.get('$$producto').value.id });
          f.patchValue({ descripcionHTML: f.get('descripcionHTML').value + "  - " + f.get('$$producto').value.id });
        }
        if (this.dataBarCode.codigoEspecial) {
          f.patchValue({ descripcionNormal: f.get('descripcionNormal').value + "  - " + f.get('$$producto').value.codigoEspecial });
          f.patchValue({ descripcionHTML: f.get('descripcionHTML').value + "  - " + f.get('$$producto').value.codigoEspecial });
        }
      }
    }
  }
  removeItem(item) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
  }
  ngAfterViewInit(): void {
    this.loadSelect();
  }

  loadSelect() {
    let data = [{
      id: 1,
      cols: 5,
      text: 'Estilo 1',
      altura: '80px',
      ancho: '120px',
      selected: true,
      showTipoBarCode: true
    }, {
      id: 2,
      cols: 5,
      text: 'Estilo 2 (5 col)',
      altura: '45px',
      ancho: '120px',
      showTipoBarCode: false
    }, {
      id: 3,
      cols: 4,
      text: 'Estilo 2 (4 col)',
      altura: '55px',
      ancho: '120px',
      showTipoBarCode: false
    }, {
      id: 4,
      cols: 3,
      text: 'Estilo 2 (3 col)',
      altura: '62px',
      ancho: '120px',
      showTipoBarCode: false
    }, {
      id: 5,
      cols: 2,
      text: 'Estilo 2 (2 col)',
      altura: '75px',
      ancho: '120px',
      showTipoBarCode: false
    }, {
      id: 30,
      cols: 2,
      text: 'Estilo 6 custom (2 col)',
      altura: '90px',
      ancho: '120px',
      showTipoBarCode: false
    }];

    $("#cmbTipoStyle").select2({
      theme: 'bootstrap4',
      width: '100%',
      data: data,
      allowClear: true,
      placeholder: 'Seleccione el estilo',
      templateResult: this.formatRepoProdPadre,
      templateSelection: (option) => {
        if (option.id.length > 0) {
          return option.text;
        } else {
          return option.text;
        }
      },
      escapeMarkup: (m) => {
        return m;
      }
    });
    $('#cmbTipoStyle').on('select2:select', (e) => {
      let data = e.params.data;
      this.dataBarCode.cols = data.cols;
      this.dataBarCode.showTipoBarCode = data.showTipoBarCode;
      this.dataBarCode.tipoBarCode = this.dataBarCode.codigos[0].id;
      if (!data.showTipoBarCode) {
        this.dataBarCode.tipoBarCode = "default";
      } else {
        this.dataBarCode.tipoBarCode = this.dataBarCode.codigos[0].id;
      }
      setTimeout(() => {
        $("#nombreProd0").focus();
      });
      this.cdr.markForCheck();
    });
  }
  getData() {
    let request = [];
    let reg = new RegExp("%");
    this.fi.controls.forEach((f: FormGroup) => {
      let item: any = {};
      item = {
        id: f.get('id').value,
        cantidad: f.get('cantidad').value,
        codigo: f.get('codigo').value,
        precio: f.get('precio').value
      }
      if (this.dataBarCode.texto.id == 1) {
        item.descripcion = f.get('descripcionNormal').value;
      }
      if (this.dataBarCode.texto.id == 2) {
        item.descripcion = f.get('descripcionHTML').value;
      }
      request.push(item);
    });
    return request;
  }
  printBarCode() {
    if (this.f.items.valid) {
      let data = this.getData();
      this.dataPrint = {
        data: data,
        cols: this.dataBarCode.cols,
        style: $("#cmbTipoStyle").val(),
        tipo: this.dataBarCode.tipoBarCode
      }
      this.productosHTTPService.generateBarCodePDF(data, this.dataPrint.cols.toString(), this.dataPrint.style, this.dataPrint.tipo, "pdf").subscribe((response: any) => {
        this.dataBarCode.showIframe = true;
        let blob = new Blob([response], { type: "application/pdf" });
        let fileURL = URL.createObjectURL(blob);
        this.renderer.setProperty(this.pdfBarcode.nativeElement, 'src', fileURL);
        this.cdr.markForCheck();
      });
    }
  }
  formatRepoProdPadre(option, data) {
    if (option.loading) {
      return "Buscando el estilo...";
    }
    if (!option.id) {
      return option.text;
    }
    let ob = '<div class="row" style="margin: 0px !important;" ><div class="col-md-7">' + option.text + ''
      + '</div><div class="col-md-5">'
      + '<img class="img-responsive img-thumbnail" style="width: ' + option.ancho + '; height: ' + option.altura + ';" src="assets/images/barCode' + option.id + '.png" />'
      + '</div>';
    +'</div>';
    return ob;
  }

  validateBarCode(dataBarCode: any): ValidatorFn {
    return (control: AbstractControl): { invalidCode: boolean } => {
      let valid = null;
      if (dataBarCode.tipoBarCode == "default") {
        valid = true;
      } else {
        try {
          new JsBarcode($("#barcode"), control.value, {
            format: dataBarCode.tipoBarCode
          });
          valid = true;
        } catch (e) {
          valid = false;
        }
      }
      return valid ? null : { invalidCode: true };
    };
  }
  downloadBarCode(typePrint) {
    this.productosHTTPService.generateBarCodePDF(this.dataPrint.data, this.dataPrint.cols.toString(), this.dataPrint.style, this.dataPrint.tipo, typePrint).subscribe((response: any) => {
      let nameFile = "ListaCodBarras-" + moment().format('DMMYYYYhmmss');
      let blob = new Blob([response], { type: "application/" + typePrint });
      download(blob, nameFile + "." + typePrint, "file/" + typePrint);
    });
  }
  // validateSucursal(c: AbstractControl): { invalidCode: boolean } {
  //   let valid = null;
  //   if ($scope.dataBarCode.tipoBarCode == "default") {
  //     valid = true;
  //   } else {
  //     try {
  //       var index = $(val2).attr("data-index");
  //       new JsBarcode($("#barcode"), $scope.data.codigo[index], {
  //         format: $scope.dataBarCode.tipoBarCode
  //       });
  //       valid = true;
  //     } catch (e) {
  //       valid = false;
  //     }
  //   }
  //   if (c.get('sucursalOrigen').value == c.get('sucursalDestino').value) {
  //     return { invalidCode: true };
  //   }
  // }
  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

