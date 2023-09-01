import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { ISelect } from '../../../../../../core/interfaces/iselect';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ClienteHTTPService } from 'src/app/core/services/http/cliente-http.service';
import { SearchCliModal } from '../../modal/search/search-cli.modal';
import { Cliente } from 'src/app/core/models/cliente';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailCliComponent } from '../detail/detail-cli.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { shareReplay } from 'rxjs/operators';
import { SelectCliService } from './select-cli.service';
import { ObjectValidator } from 'src/app/core/utils/object-validator';
import { TipoSearch } from 'src/app/core/enums';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectCliComponent),
  multi: true
};
@Component({
  selector: 'select-cli',
  templateUrl: './select-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectCliComponent implements OnInit, AfterViewInit, ControlValueAccessor, ISelect {

  public TipoSearch = TipoSearch;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  data: Cliente = null;
  cliente: Cliente = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  @Input()
  disabled: boolean = false;
  public searching: boolean = false;
  public innerValue: any = '';
  propagateChange = (_: any) => { }
  @Output()
  eventSelectCli = new EventEmitter<any>();
  private objectValidator: ObjectValidator = new ObjectValidator();
  @Input()
  public typeSearch: TipoSearch = TipoSearch.SEARCH_FILTER;

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private clienteHTTPService: ClienteHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal,
    private messageToast: ToastrService,
    private selectCliService: SelectCliService
  ) {
  }

  ngOnInit() {

  }
  ngAfterViewInit(): void {
    
  }
  /**
   * 
   * @param data es el dato que viene para buscar
   * en los cambios no se propagan porque se supone que de donde viene
   * ya tiene el dato cargado tal como esta, lo importante es el id, esta parte
   * seria para completar el label nomas compo por ejemplo el nombre
   */
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
          this.selectCliService.loadData(data.id).subscribe(resp => {
            this.setData(resp, false);
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
  search(value: string) {
    if (this.isValid(value)) {
      this.searching = true;
      this.clienteHTTPService.getClienteByMultipleFilter(value, 2, "id", 1, 10, true).pipe(shareReplay({ bufferSize: 1, refCount: true }))
        .subscribe((resp: RespPagination<Cliente>) => {
          this.searching = false;
          if (resp.total > 0) {
            if (resp.total == 1) {
              this.setData(resp.data[0]);
            } else {
              const search = this.modalService.open(SearchCliModal, { size: 'xl', backdrop: 'static' });
              search.componentInstance.typeSearch = this.typeSearch;
              search.componentInstance.title= "Listado de clientes";
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
  setLabel(data: any) {
    if (data.entidad.persona) {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.entidad.persona.apellido + ' ' + data.entidad.persona.nombre + " - " + data.nroDocCliente);
    }
    if (data.entidad.empresa) {
      this.renderer.setProperty(this.input.nativeElement, 'value', data.entidad.empresa.razonSocial + " - " + data.nroDocCliente);
    }
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
    this.cdr.markForCheck();
  }
  setData(data: Cliente, event = true) {
    if (this.isNotEqualValue(data)) {
      this.cliente = data;
      this.selectCliService.saveData(data.id, data);
      this.setLabel(data);
      this.innerValue = data;
      if (event) {
        this.propagateChange(this.innerValue);
        this.eventNextFocus();
        this.eventSelectCli.emit(this.innerValue);
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
        if (data.persona && this.innerValue.persona) {
          if (data.id == this.innerValue.id &&
            data.persona.nombre == this.innerValue.persona.nombre &&
            data.persona.apellido == this.innerValue.persona.apellido &&
            data.persona.dni == this.innerValue.persona.dni) {
            equal = false;
          }
        }
      }
    }
    return equal;
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
      this.eventInitFocus();
      this.eventSelectCli.emit(this.innerValue);
    }
    this.cdr.markForCheck();
  }

  isValid(value: string): boolean {
    if (value == null) return false;
    if (value.replace(/\s/g, "").length == 0) return false;
    return true;
  }
  showSearchCli() {
    const modalSearchCli = this.modalService.open(SearchCliModal, { size: 'xl', backdrop: 'static' });
    modalSearchCli.result.then((data) => {
      this.setData(data);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  showDetail() {
    const modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailCliComponent, { idEntity: this.innerValue.id });
    modalDetail.componentInstance.title = 'Detalle del cliente seleccionado ';
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

