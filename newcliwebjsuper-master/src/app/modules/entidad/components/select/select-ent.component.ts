import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { EntidadHTTPService } from '../../../../core/services/http/entidad-http.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Entidad } from '../../../../core/models/entidad';
import { DialogGenComponent } from '../../../shared/components/dialog/gen/dialog-gen.component';
import { SearchEntModal } from '../../modals/search/search-ent.modal';
import { DetailEmpComponent } from 'src/app/modules/empresa/modules/shared-prov/components/detail/detail-emp.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailPersonaComponent } from 'src/app/modules/shared/components/persona/detail/detail-per-modal.component';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectEntComponent),
  multi: true
};
@Component({
  selector: 'select-ent',
  templateUrl: './select-ent.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectEntComponent implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  data: Entidad = null;
  persona: Entidad = null;
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  @Input()
  disabled: boolean = false;
  public searching: boolean = false;
  public innerValue: any = '';
  propagateChange = (_: Entidad) => { }

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private personaHTTPService: EntidadHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal,
    private messageToast: ToastrService
  ) {
  }

  ngOnInit() {
  }
  search(value: string) {
    if (this.isValid(value)) {
      this.searching = true;
      this.personaHTTPService.getEntidadByMultipleFilter(value, "id", 1, 10, true).subscribe((resp: RespPagination<Entidad>) => {
        this.searching = false;
        if (resp.total > 0) {
          if (resp.total == 1) {
            this.setData(resp.data[0]);
          } else {
            const search = this.modalService.open(SearchEntModal, { size: 'xl', backdrop: 'static' });
            search.componentInstance.data = resp;
            search.componentInstance.text = value;
            search.result.then((entidad) => {
              this.setData(entidad);
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
  loadData(data: any) {
    this.data = null;
    if (data) {
      if (data.hasOwnProperty('tipo')) {
        if (data.tipo == 'PERSONA') {
          let persona = data.persona;
          if (persona.hasOwnProperty('apellido') && persona.hasOwnProperty('nombre') && persona.hasOwnProperty('dni') && persona.hasOwnProperty('id')) {
            this.data = data;
          }
        }
        if (data.tipo == 'EMPRESA') {
          let empresa = data.empresa;
          if (empresa.hasOwnProperty('razonSocial') && empresa.hasOwnProperty('cuit') && empresa.hasOwnProperty('id')) {
            this.data = data;
          }
        }
      }
    }
    if (this.data) {
      this.setData(this.data, false);
    } else {
      this.resetData(false);
    }
  }
  setData(data: Entidad, event = true) {
    this.persona = data;
    this.setLabel(data);
    this.innerValue = data;
    if (event) {
      this.propagateChange(this.innerValue);
      this.eventNextFocus();
    }
    this.cdr.markForCheck();
  }
  setLabel(data: Entidad) {
    if (data.tipo == 'PERSONA') {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.persona.apellido + ' ' + data.persona.nombre + " - " + data.persona.dni);
    }
    if (data.tipo == 'EMPRESA') {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.empresa.razonSocial + ' ' + data.empresa.cuit);
    }
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
  }
  eventNextFocus() {
    if (this.nextFocus) {
      setTimeout(() => {
        $("#" + this.nextFocus).focus()
      });
    }
  }
  eventInitFocus() {
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }
  resetData(event = true) {
    this.innerValue = null;
    this.renderer.setProperty(this.input.nativeElement, 'value', '');
    this.renderer.setProperty(this.input.nativeElement, 'disabled', false);
    if (event) {
      this.propagateChange(this.innerValue);
    }
    this.cdr.markForCheck();
  }

  removeData() {
    this.eventInitFocus();
    this.resetData();
  }

  isValid(value: string) {
    if (value == null) return false;
    if (value.replace(/\s/g, "").length == 0) return false;
    return true;
  }

  ngAfterViewInit(): void {

  }
  showSearchEnt() {
    const modalSearchEnt = this.modalService.open(SearchEntModal, { size: 'xl', backdrop: 'static' });
    modalSearchEnt.result.then((data) => {
      this.setData(data);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  showDetail() {
    if (this.innerValue.tipo == 'EMPRESA') {
      let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
      modalDetail.componentInstance.item = new AdItem(DetailEmpComponent, { idEntity: this.innerValue.id });
      modalDetail.componentInstance.title = 'Detalle de datos de la empresa ' + this.innerValue.empresa.razonSocial;

    }
    if (this.innerValue.tipo == 'PERSONA') {
      let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
      modalDetail.componentInstance.item = new AdItem(DetailPersonaComponent, { idEntity: this.innerValue.id });
      modalDetail.componentInstance.title = 'Detalle de datos de la persona ' + this.innerValue.persona.nombre + ' ' + this.innerValue.persona.apellido;

    }
  }
  new() {
    // const modalNew = this.modalService.open(NewPerModal, { size: 'xl', backdrop: 'static' });
    // modalNew.result.then((persona) => {
    //   this.setData(persona);
    // }, (reason) => {
    //   this.eventInitFocus();
    // });
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

