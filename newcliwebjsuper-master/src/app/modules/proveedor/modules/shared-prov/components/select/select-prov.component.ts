import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { ISelect } from '../../../../../../core/interfaces/iselect';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { ObjectValidator } from '../../../../../../core/utils/object-validator';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { shareReplay } from 'rxjs/operators';
import { SelectProvService } from './select-prov.service';
import { Proveedor } from 'src/app/core/models/proveedor';
import { ProveedorHTTPService } from 'src/app/core/services/http/proveedor-http.service';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailProvComponent } from '../detail/detail-prov.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { SearchProvModal } from '../../modals/search/search-prov.modal';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectProvComponent),
  multi: true
};
@Component({
  selector: 'select-prov',
  templateUrl: './select-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectProvComponent implements OnInit, AfterViewInit, ControlValueAccessor, ISelect {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  // @Input()
  // data: any = null;
  proveedor: null = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  // @Input()
  // typeResult: number = 1; //1: objeto / 2: id del producto
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  @Input()
  disabled: boolean = false;
  public searching: boolean = false;
  public innerValue: any = '';
  propagateChange = (_: any) => { }
  @Output()
  eventSelectProv = new EventEmitter<any>();
  @Input()
  public index: number = 0;
  private objectValidator: ObjectValidator = new ObjectValidator();
  // private cache: any = {};
  constructor(
    protected confirmationService: ConfirmationServiceService,
    private proveedorHTTPService: ProveedorHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal,
    private messageToast: ToastrService,
    private selectProvService: SelectProvService
  ) {
  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
  }
  search(value: string) {
    if (this.isValid(value)) {
      this.searching = true;
      this.proveedorHTTPService.getProveedoresByMultipleFilter(value, "id", 1, 10, true).pipe(shareReplay({ bufferSize: 1, refCount: true }))
        .subscribe((resp: RespPagination<any>) => {
          this.searching = false;
          if (resp.total > 0) {
            if (resp.total == 1) {
              this.setData(resp.data[0]);
            } else {
              const search = this.modalService.open(SearchProvModal, { size: 'xl', backdrop: 'static' });
              search.componentInstance.data = resp;
              search.componentInstance.text = value;
              search.result.then((data) => {
                this.setData(data);
              }, (reason) => {
                this.cdr.markForCheck();
                this.eventInitFocus();
              });
            }
          } else {
            this.messageToast.warning("Sin resultados para la busqueda (" + value + ")");
            this.cdr.markForCheck();
          }
        })
    } else {
      this.messageToast.error("Debe ingresar una busqueda");
    }
  }
  private loadData(data: any) {
    let loadData = false;
    // primer filtro para comprobar que sea una persona lo que llega
    if (data) {
      if (this.objectValidator.isValidPersona(data) || this.objectValidator.isValidEmpresa(data)) {
        loadData = true;
        this.setLabel(data);
      }
      if (!loadData) {
        if (data.hasOwnProperty('id')) {
          loadData = true;
          this.proveedorHTTPService.getProveedorMin(data.id).subscribe(resp => {
            this.setData(resp.data, false);
          });
        }
      }
      if (!loadData) {
        this.resetData(false);
      }
    } else {
      this.resetData(false);
    }
  }
  setLabel(data: any) {
    if (data.entidad.persona) {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.entidad.persona.apellido + ' ' + data.entidad.persona.nombre + " - " + data.nroDocProveedor);
    }
    if (data.entidad.empresa) {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.entidad.empresa.razonSocial + " - " + data.nroDocProveedor);
    }
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
    this.cdr.markForCheck();
  }
  setData(data: any, event = true) {
    if (this.isNotEqualValue(data)) {
      this.proveedor = data;
      this.selectProvService.saveData(data.id, data);
      this.setLabel(data);
      this.innerValue = data;
      if (event) {
        this.propagateChange(this.innerValue);
        this.eventNextFocus();
        this.eventSelectProv.emit(this.innerValue);
      }
      this.cdr.markForCheck();
    }
  }

  /**
   * Propagamos o cambiamos solo si el valor no es igual al que ya estaba antes, como los componentes existentes
   */
  isNotEqualValue(data) {
    let equal = true;
    if (data == this.innerValue) {
      equal = false;
    } else {
      if (data && this.innerValue) {
        if (data.id == this.innerValue.id &&
          data.razonSocial == this.innerValue.razonSocial &&
          data.cuit == this.innerValue.cuit) {
          equal = false;
        }
      }
    }
    return equal;
  }
  eventNextFocus() {
    if (this.nextFocus) {
      setTimeout(() => {
        $("#" + this.nextFocus).focus()
        $("#" + this.nextFocus).select()
      });
    }
  }
  eventInitFocus() {
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }
  resetData(event = true, focus = true) {
    this.innerValue = null;
    this.renderer.setProperty(this.input.nativeElement, 'value', '');
    this.renderer.setProperty(this.input.nativeElement, 'disabled', false);
    if (event) {
      this.propagateChange(this.innerValue);
      if (focus) {
        this.eventInitFocus();
      }
      this.eventSelectProv.emit(this.innerValue);
    }
    this.cdr.markForCheck();
  }

  isValid(value: string): boolean {
    if (value == null) return false;
    if (value.replace(/\s/g, "").length == 0) return false;
    return true;
  }

  showSearchProv() {
    const modalSearchProv = this.modalService.open(SearchProvModal, { size: 'xl', backdrop: 'static' });
    modalSearchProv.result.then((data) => {
      this.setData(data);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  showDetail() {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailProvComponent, { idEntity: this.innerValue.id });
    modalDetail.componentInstance.title = 'Detalle de datos | ' + this.innerValue.razonSocial;
  }

  writeValue(obj: any): void {
    setTimeout(() => {
      this.innerValue = obj;
      this.loadData(this.innerValue);
    });
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }

}

