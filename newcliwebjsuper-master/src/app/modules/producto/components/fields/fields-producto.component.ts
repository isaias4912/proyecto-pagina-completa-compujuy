import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter, ViewChildren, QueryList } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Producto } from '../../../../core/models/producto';
import { ProductoPadre } from '../../../../core/models/producto-padre';
import { Familia } from '../../../../core/models/familia';
import { ProductosHTTPService } from '../../../../core/services/http/product-http.service';
import { FormBuilder, FormGroup, Validators, AbstractControl, FormArray, ValidatorFn } from '@angular/forms';
import { IDataFormProducto } from '../../models/idata-form-producto';
import { ImagenProducto } from 'src/app/core/models/imagen-producto';
import { environment } from '../../../../../environments/environment'
import { IDataImage } from 'src/app/core/models/idata-image';
import { ImageUploadComponent } from 'src/app/modules/shared/components/image-upload/image-upload.component';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { MenuItem } from 'src/app/modules/shared/models/menu-item';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { SelectProductParentComponent } from 'src/app/modules/prod-padre/modules/shared-prod-padre/components/select/select-product-parent.component';
declare var $: any;
import * as Quill from 'quill'
import ImageResize from 'quill-image-resize-module';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { SelectFamilyComponent } from 'src/app/modules/familia/components/select/select-family.component';
import { PathService } from 'src/app/core/services/utils/path.service';
Quill.register('modules/imageResize', ImageResize);
@Component({
  selector: 'fields-producto',
  templateUrl: './fields-producto.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsProductoComponent extends ComponentPage implements OnInit, AfterViewInit {

  @Input()
  public producto: Producto = null;
  public formInfoBasica: FormGroup;
  public formPrecios: FormGroup;
  public formExistencia: FormGroup;
  @ViewChild(SelectFamilyComponent, { static: false })
  selectFamilyComponent: SelectFamilyComponent;
  @ViewChild(SelectProductParentComponent, { static: false })
  selectProductParentComponent: SelectProductParentComponent;
  @Input()
  type: number = 1; //1: alta . 2: modif
  @Input()
  tipoProducto: 'SIMPLE' | 'COMPUESTO' = 'SIMPLE';
  @Input()
  dataNewUpdate: any = {};
  @Input()
  public dataForm: IDataFormProducto;
  @Output()
  saveEvent = new EventEmitter<any>(null);
  @Output()
  cancelEvent = new EventEmitter<null>();
  public dataImage: IDataImage = { showProgress: false, valueProgress: 0 };
  // public Editor = Editor;
  public environment = environment;
  @ViewChild(ImageUploadComponent, { static: false })
  imageUploadComponent: ImageUploadComponent;
  public configuracion: any;
  public sucursales: any;
  @ViewChild('btnNextForm', { static: false }) btnNext: ElementRef<HTMLElement>;
  public editor_modules: any = {
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
    imageResize: true
  };
  public dataUtil: any = {
    updateIva: false,
    tiposPrecioIva: [{ nombre: "Con IVA incluido", id: 1 }, { nombre: "Sin IVA incluido", id: 2 }],
    tipoPrecio: 1,
    ivas: [
      { idIva: 3, descripcion: "0%", valor: 0 },
      { idIva: 4, descripcion: "10.5%", valor: 10.5 },
      { idIva: 5, descripcion: "21.0%", valor: 21.0 },
      { idIva: 6, descripcion: "27.0%", valor: 27.0 },
      { idIva: 8, descripcion: "5.0%", valor: 5.0 },
      { idIva: 9, descripcion: "2.5%", valor: 2.5 }
    ]
  };
  public dataStock = {
    tiposCodigo: [{ label: "Cod. Normal", value: 1 }, { label: "Cod. Especial", value: 2 }],
    loadingCodigo: false
  }
  items: MenuItem[];
  activeIndex: number = 0;
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private resizeSvc: ResizeService,
    public pathService: PathService
  ) {
    super();
  }

  ngOnInit() {
    this.configuracion = this.authService.getConfiguracion();
    this.sucursales = this.authService.getSucursales();

    this.formInfoBasica = this.formBuilder.group({
      productoPadre: this.formBuilder.group({
        id: [null, [Validators.required]],
        nombre: ['', [Validators.required]],
        nombreCorto: ['', []],
        familia: this.formBuilder.group({
          id: [null, [Validators.required]],
          nombre: ['', []],
        })
      }),
      nombre: ['', [Validators.required, Validators.maxLength(1000)]],
      codigoEspecial: [{ value: '', disabled: false }, [Validators.maxLength(1000)]],
      nombreFinal: [{ value: '', disabled: false }, [Validators.required]],
      unidad: [null, [Validators.required]],
      detalle: ['', []],
      contenidoNeto: ['', [Validators.required, Validators.maxLength(30)]],
      pesable: [false, [Validators.required]],
      ingresoCantidadManual: [false, [Validators.required]],
    });
    // si es un producto compuesto agregamo un campo para los prod compuestos
    if (this.tipoProducto == 'COMPUESTO') {
      this.formInfoBasica.addControl('productosCompuestos', new FormArray([]));
    }
    this.formPrecios = this.formBuilder.group({
      $$iva: [this.getIvaFromValue(21), [Validators.required]],
      iva: [21.0, [Validators.required]],
      idIva: [5, [Validators.required]],
      precioCosto: [0, [Validators.required]],
      precioVenta: [0, [Validators.required]],
      dataUtil: this.formBuilder.group({
        tipoPrecio: [1, []],
        precioCosto: [null, []],
        pagoIva: [null, []],
        precionSIva: [null, []],
      })
    });
    this.formPrecios.updateValueAndValidity();
    this.formExistencia = this.formBuilder.group({
      codigoBarras: new FormArray([]),
      stockSucursales: new FormArray([]),
      proveedores: new FormArray([])
    });
    this.items = [{
      label: 'Información básica',
      command: (event: any) => {
        this.changeTab(event.indexPrevius, null);
      }
    },
    {
      label: 'Imágenes',
      command: (event: any) => {
        this.changeTab(event.indexPrevius, null);
      }
    },
    {
      label: 'Precios',
      command: (event: any) => {
        this.changeTab(event.indexPrevius, null);
      }
    },
    {
      label: 'Stock',
      command: (event: any) => {
        this.changeTab(event.indexPrevius, null);
      }
    }
    ];
    this.resetDataForm();
    // inicializamos el producto
    if (this.type == 2) {
      this.setProducto(this.producto);
    } else {
      this.producto = new Producto();
      this.producto.productoPadre = new ProductoPadre();
      this.producto.productoPadre.familia = new Familia();
    }
    // iniciamos el pasble
    this.changePesable();
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
        this.sizeWindow = x;
        this.changeSizes();
      });
  }

  ngAfterViewInit(): void {
    // cargamos los componenetes si es modificacion
    if (this.type == 2) {
      this.reloadComponents(this.producto);
    } else {
      this.reset();
    }
    this.loadHelp();
  }
  changeSizes() {
    setTimeout(() => {
      $('.item-pc-prod').removeClass('width-200');
      $('.item-cb-cant').removeClass('width-150');
      $('.item-cb-tipo,  .item-cb-desc, .item-ss-suc').removeClass('width-100');
      if (this.sizeWindow == 0) {
        $('.item-pc-prod').addClass('width-200');
        $('.item-cb-cant').addClass('width-150');
        $('.item-cb-tipo,  .item-cb-desc, .item-ss-suc').addClass('width-100');
      }
    });
  }
  initFocus() {
    setTimeout(() => {
      this.type == 1 ? $('#txtNombreProd').select2("open") : this.type == 2 ? $("#txtDesAdicProd").focus() : '';
    }, 800);
  }
  reloadComponents(producto: Producto) {
    if (producto.productoPadre) {
      this.selectProductParentComponent.setData([producto.productoPadre]);
      if (producto.productoPadre.familia) {
        this.selectFamilyComponent.setData([producto.productoPadre.familia]);
        this.selectFamilyComponent.setDisabled(true);
      }
    }
    // seteamos la unidad
    if (producto.unidad) {
      this.dataNewUpdate.unidades.forEach(u => {
        if (u.id == producto.unidad.id) {
          this.formInfoBasica.patchValue({ unidad: u });
        }
      });
    }
    this.c.controls.forEach(cod => {
      cod.get('codigo')['validateCode'] = true;
      cod.get('codigo')['exist'] = false;
      cod.get('codigo').setErrors(null);
    });
    this.cdr.detectChanges();
  }
  addItemProdCompuesto(index = 0) {
    this.dataForm.productoCompuesto.submitted = true;
    if (this.fpc.valid) {
      this.dataForm.productoCompuesto.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [0, [Validators.required]],
        producto: [null, [Validators.required]],
        cantidad: [1, [Validators.required, Validators.min(0), Validators.max(999999)]],
        descripcion: [null, [Validators.maxLength(150)]],
        index: [index, []],
      });
      this.fpc.push(f);
      setTimeout(() => {
        $("#txtProducto" + (this.fpc.controls.length - 1)).focus();
      });
    }
  }
  removeItemProdCompuesto(item: FormGroup) {
    this.fpc.removeAt(this.fpc.controls.indexOf(item));
    this.formInfoBasica.updateValueAndValidity();
  }
  private getProductosCompuestos() {
    let pcs: [] = this.formInfoBasica.get('productosCompuestos').value;
    let tempResp: any = [];
    if (pcs && pcs.length > 0) {
      pcs.forEach((pc: any) => {
        tempResp.push({
          cantidad: pc.cantidad,
          descripcion: pc.descripcion,
          producto: { id: pc.producto.id, nombre: pc.producto.nombreFinal }
        });
      });
    } else {
      this.messageToast.error('Ocurrio un error, no se registras productos cargados para ser compuesto.');
    }
    return tempResp;
  }
  public setProducto(producto: Producto) {
    this.formInfoBasica.patchValue({
      productoPadre: this.producto.productoPadre,
      nombre: this.producto.nombre,
      codigoEspecial: this.producto.codigoEspecial,
      nombreFinal: this.producto.nombreFinal,
      unidad: this.producto.unidad,
      detalle: this.producto.detalle,
      contenidoNeto: this.producto.contenidoNeto,
      pesable: this.producto.pesable ? this.producto.pesable : false,
      ingresoCantidadManual: this.producto.ingresoCantidadManual ? this.producto.ingresoCantidadManual : false
    });
    // this.formInfoBasica.patchValue(producto);
    let tempIva = this.getIvaFromValue(producto.iva);
    this.formPrecios.patchValue({
      $$iva: tempIva,
      iva: tempIva ? tempIva.valor : null,
      idIva: tempIva ? tempIva.idIva : null,
      precioCosto: producto.precioCosto,
      precioVenta: producto.precioVenta
    });
    // if ()
    // this.formPrecios.patchValue(producto);
    producto.codigoBarras.forEach(cod => {
      this.c.push(this.formBuilder.group({
        id: [cod.id, [Validators.required]],
        codigo: [cod.codigo, [Validators.required, RxwebValidators.unique(),
        this.validateOkBarCodeDB(),
        this.validateExisteBarCodeDB()
        ]],
        $$codigo: [cod.codigo, []],
        tipo: [cod.tipo, Validators.required],
        descripcion: [cod.descripcion, []],
      }));
    });
    producto.stockSucursales.forEach(ss => {
      this.ss.push(this.formBuilder.group({
        sucursal: [this.sucursales[this.sucursales.findIndex(s => s.id == ss.sucursal.id)], [Validators.required]],
        $$idSucursal: [ss.sucursal.id, [Validators.required, RxwebValidators.unique()]],
        puntoReposicion: [ss.puntoReposicion, [Validators.max(99999999)]],
        stockMinimo: [ss.stockMinimo, [Validators.max(99999999)]],
        ubicacion: [ss.ubicacion, [Validators.maxLength(250)]],
        detalle: [ss.detalle, [Validators.maxLength(250)]],
      }));
    });
    let index = 0;
    producto.proveedores.forEach(prov => {
      this.pr.push(this.formBuilder.group({
        proveedor: [prov, [Validators.required]],
        $$idProveedor: [prov.id, [Validators.required, RxwebValidators.unique()]],
        index: [index, []],
      }));
      index++;
    });
    // si el producto es compuesto
    index = 0;
    if (producto.tipo == 2) {
      producto.productosCompuestos.forEach(prod => {
        this.fpc.push(this.formBuilder.group({
          id: [prod.id, [Validators.required]],
          producto: [prod.producto, [Validators.required]],
          cantidad: [prod.cantidad, [Validators.required, Validators.min(0), Validators.max(999999)]],
          descripcion: [prod.descripcion, [Validators.maxLength(150)]],
          index: [index, []],
        }));
        index++;
      });
    }
  }

  changeTab(activeIndex: number = 0, next: boolean = null) {
    if (activeIndex === 0) {//informacion basica
      this.dataForm.informacionBasica.submitted = true;
      this.dataForm.productoCompuesto.submitted = true;
      if (this.formInfoBasica.invalid) {
        this.activeIndex = activeIndex;
        return;
      } else {
        this.setDataInformacionBasica();
      }
    }
    if (activeIndex == 1) {//imagenes
      this.dataForm.imagenes.submitted = true;
    }
    if (activeIndex == 2) {//precios
      this.dataForm.precios.submitted = true;
      if (this.formPrecios.invalid) {
        this.activeIndex = activeIndex;
        return;
      } else {
        this.setDataPrecios();
      }
    }
    if (activeIndex == 3) {//precios
      this.dataForm.existencia.stockSucursal.submitted = true;
      this.dataForm.existencia.codigosBarra.submitted = true;
      this.dataForm.existencia.proveedores.submitted = true;
      if (this.formExistencia.invalid) {
        this.activeIndex = activeIndex;
        return;
      } else {
        this.setDataExistencia();
      }
    }
    if (next != null && next != undefined) {
      if (next) {
        this.activeIndex++;
      } else {
        this.activeIndex--;
      }
    }
  }
  selectValueProdPadre(data) {
    let item = data;
    if (item.familia) {
      this.getCodigoEspecial(item.familia.id);
      this.selectFamilyComponent.setDisabled(true);
      this.selectFamilyComponent.setData([item.familia]);
      this.formInfoBasica.patchValue({ productoPadre: { familia: item.familia } });

    }
    if (item.nuevo) {
      this.formInfoBasica.controls['codigoEspecial'].setValue(null);
      this.selectFamilyComponent.clear();
      this.selectFamilyComponent.setDisabled(false);
    }
    // if (this.type == 1) {
    this.formInfoBasica.patchValue({ productoPadre: { nombre: item.nombre } });
    this.formInfoBasica.patchValue({ productoPadre: { nombreCorto: item.nombreCorto } });
    this.formInfoBasica.patchValue({ productoPadre: { id: item.nuevo ? 0 : item.id } });
    this.formInfoBasica.patchValue({ nombreFinal: item.nombreCorto + " " + this.formInfoBasica.get('nombre').value });
    // }
    $("#txtDesAdicProd").focus();
  }
  selectValueFamilia(data) {
    console.log('datassssssssssssssssss', data)
    let item = data;
    this.getCodigoEspecial(item.id);
    this.formInfoBasica.patchValue({ productoPadre: { familia: item } });
  }

  changeNombre() {
    this.formInfoBasica.patchValue({ nombreFinal: this.formInfoBasica.get('productoPadre.nombreCorto').value + " " + this.formInfoBasica.get('nombre').value });
  }

  getCodigoEspecial(idFamilia: number) {
    this.productosHTTPService.getCodigoEspecial(idFamilia).subscribe(resp => {
      this.formInfoBasica.patchValue({ codigoEspecial: resp.data });
      this.formInfoBasica.get('codigoEspecial').updateValueAndValidity();
    });
  }

  public getFormInfoBasica() {
    return this.formInfoBasica;
  }

  get formIB() {
    return this.formInfoBasica.controls;
  }
  get fpc() {
    return this.formIB.productosCompuestos as FormArray;;
  }

  save(option = 1) {
    if (this.activeIndex == 0) {
      this.dataForm.informacionBasica.submitted = true;
      this.dataForm.productoCompuesto.submitted = true;
      this.saveInformacionBasica(option);
    }
    if (this.activeIndex == 1) {
      this.dataForm.imagenes.submitted = true;
      this.saveImagenes(option);
    }
    if (this.activeIndex == 2) {
      this.dataForm.precios.submitted = true;
      this.savePrecios(option);
    }
    if (this.activeIndex == 3) {
      this.dataForm.existencia.codigosBarra.submitted = true;
      this.dataForm.existencia.proveedores.submitted = true;
      this.dataForm.existencia.stockSucursal.submitted = true;
      this.saveExistencia(option);
    }
  }
  saveInformacionBasica(option = 1) {
    if (this.formInfoBasica.valid) {
      this.setDataInformacionBasica();
      this.saveEvent.emit({ option: option, producto: this.producto });
    }
  }
  savePrecios(option = 1) {
    if (this.formPrecios.valid) {
      this.setDataPrecios();
      this.saveEvent.emit({ option: option, producto: this.producto });
    }
  }
  saveImagenes(option = 1) {
    this.saveEvent.emit({ option: option, producto: this.producto });
  }
  saveExistencia(option = 1) {
    if (this.formExistencia.valid) {
      this.setDataExistencia();
      this.saveEvent.emit({ option: option, producto: this.producto });
    }
  }

  cancel() {
    this.cancelEvent.emit();
  }
  next() {
    this.changeTab(this.activeIndex, true)
  }

  previous() {
    this.changeTab(this.activeIndex, false)
  }
  resetDataForm() {
    this.dataForm = {
      existencia: {
        codigosBarra: { submitted: false },
        stockSucursal: { submitted: false },
        proveedores: { submitted: false }
      },
      informacionBasica: { submitted: false },
      imagenes: { submitted: false },
      precios: { submitted: false },
      productoCompuesto: { submitted: false },
    }
  }
  reset() {
    this.producto = new Producto();
    this.formInfoBasica.reset();
    this.formInfoBasica.patchValue({
      nombre: '',
      codigoEspecial: '',
      nombreFinal: '',
      unidad: null,
      detalle: '',
      contenidoNeto: '',
      pesable: false,
      ingresoCantidadManual: false,
    });
    if (this.tipoProducto == 'COMPUESTO') {
      this.fpc.clear();
    }
    this.formInfoBasica.get('productoPadre').patchValue({
      id: null,
      nombre: '',
      nombreCorto: '',
    });
    this.formInfoBasica.get('productoPadre').get('familia').patchValue({
      id: null,
      nombre: '',
    });
    this.formPrecios.reset();
    this.formPrecios.patchValue({
      $$iva: this.getIvaFromValue(21),
      precioCosto: 0,
      precioVenta: 0,
      iva: 21.0,
      idIva: 5
    });
    this.formPrecios.get('dataUtil').patchValue({
      tipoPrecio: 1,
      precioCosto: null,
      pagoIva: null,
      precionSIva: null
    });
    this.formExistencia.reset();
    this.c.clear();
    this.ss.clear();
    this.pr.clear();
    this.resetDataForm();
    this.selectProductParentComponent.clear();
    console.log('this.selectFamilyComponent', this.selectFamilyComponent)
    this.selectFamilyComponent.clear();
    this.selectFamilyComponent.setDisabled(false);
    this.activeIndex = 0;
    this.initFocus();
    if (this.tipoProducto == 'COMPUESTO') {
      this.addItemProdCompuesto();
    }
    this.cdr.detectChanges();
  }
  setDataInformacionBasica() {
    this.producto.nombre = this.formInfoBasica.get('nombre').value;
    this.producto.codigoEspecial = this.formInfoBasica.get('codigoEspecial').value;
    this.producto.nombreFinal = this.formInfoBasica.get('nombreFinal').value;
    this.producto.unidad = this.formInfoBasica.get('unidad').value;
    this.producto.detalle = this.formInfoBasica.get('detalle').value;
    this.producto.contenidoNeto = this.formInfoBasica.get('contenidoNeto').value;
    this.producto.productoPadre = this.formInfoBasica.get('productoPadre').value;
    this.producto.precioCosto = this.formPrecios.get('precioCosto').value;
    this.producto.precioVenta = this.formPrecios.get('precioVenta').value;
    this.producto.iva = this.formPrecios.get('iva').value;
    this.producto.pesable = this.formInfoBasica.get('pesable').value;
    this.producto.ingresoCantidadManual = this.formInfoBasica.get('ingresoCantidadManual').value;
    if (this.tipoProducto == 'COMPUESTO') {
      this.producto.productosCompuestos = this.getProductosCompuestos();;
    }
  }
  loadImage(resp) {
    if (resp.target == "addImageProducto") {
      this.dataImage.showProgress = false;
      this.producto.imagenProducto.forEach(img => {
        this.verifiedOrden(img);
      });
      resp.data[0].orden = this.getMaxOrder() + 1;
      let imageProducto = new ImagenProducto();
      imageProducto.tipo = resp.data[0].fileType;
      imageProducto.alt = resp.data[0].fileAlt;
      imageProducto.nombre = resp.data[0].fileName;
      imageProducto.tamanio = resp.data[0].fileSize;
      imageProducto.orden = resp.data[0].orden;
      this.producto.imagenProducto.push(imageProducto);
    }
  }
  // ----------------------IMAGENES--------------------------
  removeImage(imagen) {
    let index = this.producto.imagenProducto.indexOf(imagen);
    this.producto.imagenProducto.splice(index, 1);
  };
  cancelUploadIMage() {
    this.dataImage.showProgress = false;
    this.imageUploadComponent.cancelUploadIMage();
  }
  removeAll() {
    this.producto.imagenProducto = new Array<ImagenProducto>();
  };
  upImageOrder(image, index) {
    this.verifiedOrden(image);
    if (image.orden > 1) {
      var newOrder = (image.orden - 1);
      this.producto.imagenProducto.forEach(img => {
        if (img.orden == newOrder) {
          img.orden++;
        }
      });
      image.orden = newOrder;
      this.producto.imagenProducto.sort((a, b) => a.orden.toString().localeCompare(b.orden.toString()));
    }
    // $("#tr" + index).effect("highlight", { color: '#7CC992' }, 800, null);
  }
  downImageOrder(image, index) {
    this.verifiedOrden(image);
    if (image.orden < (this.producto.imagenProducto.length)) {
      let newOrder = (image.orden + 1);
      this.producto.imagenProducto.forEach(img => {
        if (img.orden == newOrder) {
          img.orden--;
        }
      });
      image.orden = newOrder;
      this.producto.imagenProducto.sort((a, b) => a.orden.toString().localeCompare(b.orden.toString()));
    }
    // $("#tr" + index)
    // .effect("highlight", { color: '#418fbf' }, 800, null);
  }
  reorderImage() {
    let i = 1;
    this.producto.imagenProducto.forEach(imagen => {
      imagen.orden = i;
      i++;
    });
  }
  verifiedOrden(image) {
    if (image.orden == undefined || image.orden == null) {
      this.reorderImage();
    }
  }
  getMaxOrder() {
    let i = 1;
    let max = 0;
    this.producto.imagenProducto.forEach(imagen => {
      if (i == 1) {
        max = imagen.orden;
      } else {
        if (imagen.orden > max) {
          max = imagen.orden;
        }
      }
      i++;
    });
    return max;
  }
  progressUpImage(data) {
    this.dataImage.showProgress = true;
    this.dataImage.valueProgress = data.value;
  }

  //-----------precios--------------------------------------------------------------
  getIdIvaFromValue(value) {
    var tempRes = null;
    this.dataUtil.ivas.forEach(iva => {
      if (value == iva.valor) {
        tempRes = iva.idIva;
      }
    });
    return tempRes;
  };
  getIvaFromValue(value) {
    var tempRes = null;
    this.dataUtil.ivas.forEach(iva => {
      if (value == iva.valor) {
        tempRes = iva;
      }
    });
    return tempRes;
  };
  changeValuePrice() {
    let dataUtil = this.formPrecios.get('dataUtil').value;
    let producto: any = {};
    let iva = this.formPrecios.get('iva').value;
    if (dataUtil.tipoPrecio == 1) {
      dataUtil.precionSIva = dataUtil.precioCosto / ((iva / 100) + 1);
      dataUtil.pagoIva = dataUtil.precioCosto - dataUtil.precionSIva;
    } else {
      dataUtil.pagoIva = dataUtil.precioCosto * (iva / 100);
      dataUtil.precionSIva = dataUtil.precioCosto;
    }
    producto.precioCosto = dataUtil.pagoIva + dataUtil.precionSIva;
    //            redondeos-----------------------------
    producto.precioCosto = this.round2(producto.precioCosto)
    dataUtil.pagoIva = this.round2(dataUtil.pagoIva)
    dataUtil.precionSIva = this.round2(dataUtil.precionSIva);
    this.authService.getConfiguracion();
    // producto.precioVenta = (producto.precioCosto * (this.configuracion.margenGanancia / 100)) + this.producto.precioCosto;
    producto.precioVenta = (producto.precioCosto * (this.configuracion.margenGanancia / 100)) + producto.precioCosto;
    producto.precioVenta = this.round2(producto.precioVenta);
    producto.idIva = this.getIdIvaFromValue(producto.iva);
    this.formPrecios.patchValue({
      precioCosto: producto.precioCosto,
      precioVenta: producto.precioVenta,
      dataUtil: dataUtil
    });
  }
  setDataPrecios() {
    this.producto.precioCosto = this.formPrecios.get('precioCosto').value;
    this.producto.precioVenta = this.formPrecios.get('precioVenta').value;
    this.producto.iva = this.formPrecios.get('$$iva').value.valor;
    this.producto.idIva = this.formPrecios.get('$$iva').value.idIva;
  }
  // ----------------------------stocks---------------------------------
  //############################## codigos####################################3
  get fe() { return this.formExistencia.controls; }
  get c() { return this.fe.codigoBarras as FormArray; }

  changePesable() {
    if (this.formInfoBasica.get('pesable').value) {
      this.formInfoBasica.get('ingresoCantidadManual').enable();
    } else {
      this.formInfoBasica.patchValue({ ingresoCantidadManual: false });
      this.formInfoBasica.get('ingresoCantidadManual').disable();
    }
  }
  setDataExistencia() {
    let proveedores: Array<any> = this.formExistencia.get('proveedores').value;
    let proveedoresReq: Array<any> = new Array<any>();
    let codigosBarra: Array<any> = this.formExistencia.get('codigoBarras').value;
    let codigosBarraReq: Array<any> = new Array<any>();
    let stockSucursales: Array<any> = this.formExistencia.get('stockSucursales').value;
    let stockSucursalesReq: Array<any> = new Array<any>();
    proveedores.forEach((prov: any) => {
      proveedoresReq.push({ id: prov.proveedor.id, razonSocial: prov.proveedor.razonSocial });
    });
    codigosBarra.forEach((cod: any) => {
      codigosBarraReq.push({ id: cod.id, tipo: cod.tipo, codigo: cod.codigo, descripcion: cod.descripcion });
    });
    stockSucursales.forEach((ss: any) => {
      stockSucursalesReq.push({
        detalle: ss.detalle,
        puntoReposicion: ss.puntoReposicion,
        stockMinimo: ss.stockMinimo,
        ubicacion: ss.ubicacion,
        sucursal: {
          id: ss.sucursal.id,
          nombre: ss.sucursal.nombre
        }
      });
    });
    this.producto.codigoBarras = codigosBarraReq
    this.producto.proveedores = proveedoresReq;
    this.producto.stockSucursales = stockSucursalesReq;
    // esto viene de informacion  basica de productos
    this.producto.pesable = this.formInfoBasica.get('pesable').value;
    this.producto.ingresoCantidadManual = this.formInfoBasica.get('ingresoCantidadManual').value;
  }
  enableBarCode(control: any, index) {
    control['validateCode'] = false;
    control.updateValueAndValidity();
    this.cdr.markForCheck();
    setTimeout(() => {
      $("#txtStockCod" + index).focus();
    });
  };

  changeValueBarCode(control: any) {
    $("#x").prop("disabled", true);
    if (control.value == "") {

    }
    control['validateCode'] = false;
    control['exist'] = false;
    control.updateValueAndValidity();
    this.cdr.markForCheck();
  }

  controlBarCode(control: any, index, controlAux: any = null) {
    this.dataForm.existencia.codigosBarra.submitted = true;
    if (!control.hasError('required') && !control.hasError('unique')) {
      if ((this.type == 1) || (this.type == 2 && controlAux.value != control.value)) {
        this.productosHTTPService.isExistBarCode(control.value, true).subscribe(resp => {
          control['validateCode'] = true;
          control['exist'] = resp.data.existe;
          if (!resp.data.existe) {
            setTimeout(() => {
              $("#txtStockDesc" + index).focus();
            });
          }
          control.updateValueAndValidity();
          this.cdr.markForCheck();
        })
      } else {
        control['validateCode'] = true;
        control.updateValueAndValidity();
        this.cdr.markForCheck();
      }
    }
  }
  validateOkBarCodeDB(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (!control['validateCode']) {
        return { 'validateCode': true };
      }
      return null;
    };
  }
  validateExisteBarCodeDB(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['exist']) {
        return { 'exist': true };
      }
      return null;
    };
  }

  removeBarCode(codigo: any) {
    this.c.removeAt(this.c.value.findIndex(c => c.codigo === codigo.controls.codigo.value));
    this.c.patchValue(this.c.value); // refrescamos con esto para que no haya errores cuando se elimina un erroneo (se elimnina el que no aaparec emensage)
  }

  addBarCode() {
    this.dataForm.existencia.codigosBarra.submitted = true;
    if (this.formExistencia.controls.codigoBarras.valid) {
      this.c.push(this.formBuilder.group({
        id: [0, [Validators.required]],
        codigo: ['', [Validators.required, RxwebValidators.unique(),
        this.validateOkBarCodeDB(),
        this.validateExisteBarCodeDB()
        ]],
        $$codigo: ['', []],
        tipo: [1, Validators.required],
        descripcion: ["", []],
      }));
      this.dataForm.existencia.codigosBarra.submitted = false;
      setTimeout(() => {
        $("#cmbStockTipoBarCode" + (this.c.length - 1)).focus();
        this.loadHelp();
      });
    }
  }

  //--------------------existencia sucursal----------------------
  get ss(): FormArray { return this.fe.stockSucursales as FormArray; }

  removeStockSucursal(sucursal: any) {
    this.ss.removeAt(sucursal);
    this.ss.patchValue(this.ss.value);
  }

  changeSucursal(value: any) {
    if (value.controls.sucursal.value) {
      value.patchValue({ $$idSucursal: value.controls.sucursal.value.id });
    }
  }
  addStockSucursal() {
    this.dataForm.existencia.stockSucursal.submitted = true;
    if (this.formExistencia.controls.stockSucursales.valid) {
      this.ss.push(this.formBuilder.group({
        sucursal: [null, [Validators.required]],
        $$idSucursal: [0, [Validators.required, RxwebValidators.unique()]],
        puntoReposicion: [0, [Validators.max(99999999)]],
        stockMinimo: [0, [Validators.max(99999999)]],
        ubicacion: ["", [Validators.maxLength(250)]],
        detalle: ["", [Validators.maxLength(250)]],
      }));
      this.dataForm.existencia.stockSucursal.submitted = false;
      setTimeout(() => {
        $("#cmbExistSucursal" + (this.ss.length - 1)).focus();
        this.loadHelp();
      });
    }
  }

  //--------------------existencia sucursal----------------------
  get pr() { return this.fe.proveedores as FormArray; }
  addProveedor() {
    this.dataForm.existencia.proveedores.submitted = true;
    if (this.formExistencia.controls.proveedores.valid) {
      let f: FormGroup = this.formBuilder.group({
        proveedor: [null, [Validators.required]],
        $$idProveedor: [0, [Validators.required, RxwebValidators.unique()]],
      });
      this.pr.push(f);
      this.dataForm.existencia.proveedores.submitted = false;
      f.get('proveedor').valueChanges.subscribe(val => {
        f.patchValue({ $$idProveedor: val ? val.id : null });
        setTimeout(() => {
          $("#buttonAddProv" + (this.pr.controls.indexOf(f))).focus();
        });
      });
      setTimeout(() => {
        $("#selProveedor" + (this.pr.length - 1)).focus();
        this.loadHelp();
      });
    }
  }
  removeProveedor(proveedor: any) {
    this.pr.removeAt(this.pr.value.findIndex(p => p.$$idProveedor === proveedor.controls.$$idProveedor.value));
    this.pr.patchValue(this.pr.value);
  }
}

