import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { PersonaHTTPService } from '../../../../core/services/http/persona-http.service';
import { Persona } from '../../../../core/models/persona';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SearchPerModal } from '../modal/search/search-per.modal';
import { ToastrService } from 'ngx-toastr';
import { DetailPersonaComponent } from 'src/app/modules/shared/components/persona/detail/detail-per-modal.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { NewPerModal } from '../modal/new/new-per.modal';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectPerComponent),
  multi: true
};
@Component({
  selector: 'select-per',
  templateUrl: './select-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectPerComponent implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  data: Persona = null;
  persona: Persona = null;
  // @Input()
  // type: number = 1; //1: alta/ sin autocompletado . 2: modif
  // @Input()
  // typeResult: number = 1; //1: objeto / 2: id del producto
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  @Input()
  disabled: boolean = false;
  public searching: boolean = false;
  public innerValue: any = '';
  propagateChange = (_: Persona) => { }

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private personaHTTPService: PersonaHTTPService,
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
      this.personaHTTPService.getPersonaByMultipleFilter(value, "id", 1, 10, true).subscribe((resp: RespPagination<Persona>) => {
        this.searching = false;
        if (resp.total > 0) {
          if (resp.total == 1) {
            this.setData(resp.data[0]);
          } else {
            const search = this.modalService.open(SearchPerModal, { size: 'xl', backdrop: 'static' });
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
  loadData(data: any) {
    this.data = null;
    if (data) {
      if (data.hasOwnProperty('id') && data.hasOwnProperty('nombre')) {
        this.data = data;
      }
    }
    if (this.data) {
      this.setData(this.data, false);
    } else {
      this.resetData(false);
    }
  }
  setData(data: Persona, event = true) {
    this.persona = data;
    this.renderer.setProperty(this.input.nativeElement, 'value', data.apellido + ' ' + data.nombre + " - " + (data.dni));
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
    this.innerValue = data;
    if (event) {
      this.propagateChange(this.innerValue);
      this.eventNextFocus();
    }
    this.cdr.markForCheck();
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
  showSearchPer() {
    const modalSearchPer = this.modalService.open(SearchPerModal, { size: 'xl', backdrop: 'static' });
    modalSearchPer.result.then((data) => {
      this.setData(data);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  showDetail() {
    const modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailPersonaComponent, { idEntity: this.innerValue.id });
    modalDetail.componentInstance.title = 'Detalle de datos | ' + this.persona.nombre + ' ' + this.persona.apellido;
  }
  new() {
    const modalNew = this.modalService.open(NewPerModal, { size: 'xl', backdrop: 'static' });
    modalNew.result.then((persona) => {
      this.setData(persona);
    }, (reason) => {
      this.eventInitFocus();
    });
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

