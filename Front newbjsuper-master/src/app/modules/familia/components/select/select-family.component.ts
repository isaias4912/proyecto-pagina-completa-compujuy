import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ViewChild, ElementRef, forwardRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import 'select2';
import { environment } from 'src/environments/environment';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FamiliaHTTPService } from 'src/app/core/services/http/familia-http.service';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { ThreeFamModalComponent } from '../../modules/shared-fam/components/modal/three-mod/three-fam-mod.component';
import { SearchFamModal } from '../modal/search/search-fam.modal';
import { NewFamModal } from '../modal/new/new-fam.modal';

// import { NewFamModal } from 'src/app/modules/familia/components/modal/new/new-fam.modal';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectFamilyComponent),
  multi: true
};
@Component({
  selector: 'select-family',
  templateUrl: './select-family.component.html',
  styleUrls: ['./select-family.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],

})
export class SelectFamilyComponent implements OnInit, AfterViewInit, ControlValueAccessor {
  @Input()
  nextFocus: string = null;
  @Input()
  data: any;
  @Input()
  public multiple: boolean = true;
  @Input()
  disabled: boolean = false;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  selectValue = new EventEmitter<any>(false);
  event: any;
  @ViewChild('buttonClickSelect2', { static: false }) buttonSelect2: ElementRef<HTMLElement>;
  public innerValue: any = '';
  propagateChange = (_: any) => { }

  constructor(
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private familiaHTTPService: FamiliaHTTPService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    if (!this.multiple) {
      $('#' + this.name).removeAttr('multiple');
    }
    this.load();
  }
  loadData(data: any) {
    this.data = [];
    if (data) {
      if (Array.isArray(data) && data.length > 0) {
        this.familiaHTTPService.getFamiliasMinByObjs(data).subscribe(resp => {
          resp.data.forEach(item => {
            item.selected = true;
          });
          this.data = resp.data;
          this.setData(this.data);
        });
      } else {
        if (data.hasOwnProperty('id') && data.hasOwnProperty('nombre')) {
          data.select = true;
          this.data = [data];
        }
      }
    }
    this.setData(this.data);
  }

  public getData(): [] {
    let tempFamilias = $('#' + this.name).select2('data');
    let familias: any;
    familias = [];
    if (tempFamilias) {
      tempFamilias.forEach(element => {
        familias.push({ id: parseInt(element.id), nombre: element.nombre, nombreCorto: element.nombreCorto, path: element.path, nivel: element.nivel });
      });
    }
    return familias;
  }
  private load() {
    $('#' + this.name).select2({
      theme: 'bootstrap4',
      // width: '100%', // lo saque por el input group
      data: this.data,
      ajax: {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('access_token')
        },
        url: environment.baseAPIURL + "familias/all/",
        dataType: 'json',
        data: function (params) {
          var query = {
            name: params.term
          }
          return query;
        },
        processResults: function (data) {
          return {
            results: data.data
          };
        },
        cache: true
      },
      placeholder: 'Busque la familia de productos',
      language: this.languaje,
      minimumInputLength: 2,
      templateResult: function (fam) {
        return fam.nombre;
      },
      templateSelection: function (data) {
        console.log('dataddddd', data)
        if (data.id == "") {
          return data.text;
        } else {
          return data.nombre;
        }
      },
      escapeMarkup: function (m) {
        return m;
      }
    });
    $('#' + this.name).on("select2:select", (e) => {
      this.event = e;
      this.buttonSelect2.nativeElement.click();
    });
    $('#' + this.name).on("select2:unselect", (e) => {
      this.unselectValueSelect();
    });
  }
  public setData(data: any) {
    this.data = data;
    $('#' + this.name).find('option').remove();
    $('#' + this.name).select2('destroy');
    this.load()
  }
  public clear() {
    $('#' + this.name).find('option').remove();
    $('#' + this.name).val(null).trigger('change');
    this.innerValue = null;
    this.propagateChange(this.innerValue);
  }
  public setDisabled(value: boolean) {
    $('#' + this.name).prop("disabled", value);
    this.disabled = value;
    this.cdr.markForCheck();
  }
  showChartFam() {
    const modalThreeFamilias = this.modalService.open(ThreeFamModalComponent, { size: 'xl', backdrop: 'static' });
    if (this.multiple) {
      modalThreeFamilias.componentInstance.familia = null;
    } else {
      modalThreeFamilias.componentInstance.familia = this.innerValue;
    }
    this.confirmationService.confirm({
      select: (data) => {
        this.setData([data]);
        this.selectValue.emit(data);
        this.innerValue = data;
        this.propagateChange(this.innerValue);
      }
    });
  }
  showSearchFam() {
    const modalSearchFam = this.modalService.open(SearchFamModal, { size: 'xl', backdrop: 'static' });
    modalSearchFam.result.then((data) => {
      this.setInternalValue(data);
    }, (reason) => {
    });
  }
  new() {
    const modalNew = this.modalService.open(NewFamModal, { size: 'xl', backdrop: 'static' });
    modalNew.result.then((familia) => {
      this.setInternalValue(familia);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  private setInternalValue(data: any) {
    this.setData([data]);
    this.selectValue.emit(data);
    this.innerValue = data;
    this.propagateChange(this.innerValue);
    if (this.nextFocus) {
      setTimeout(() => {
        $("#" + this.nextFocus).focus()
        $("#" + this.nextFocus).select()
      });
    }
  }
  selectValueSelect() {
    if (this.event) {
      if (this.multiple) {
        let data = $('#' + this.name).select2('data');
        this.selectValue.emit(data);
        this.innerValue = data;
        this.propagateChange(this.innerValue);
      } else {
        this.selectValue.emit(this.event.params.data);
        this.innerValue = this.event.params.data;
        this.propagateChange(this.innerValue);
        if (this.nextFocus) {
          setTimeout(() => {
            $("#" + this.nextFocus).focus()
            $("#" + this.nextFocus).select()
          });
        }
      }
    }
  }
  private languaje = {
    inputTooShort: function () {
      return "Ingrese mas de 2 caracteres...";
    },
    searching: function () {
      return "Buscando…";
    },
    noResults: function () {
      return "No se encontraron resultados";
    }
  };
  eventInitFocus() {
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }

  unselectValueSelect() {
    let data = $('#' + this.name).select2('data');
    this.innerValue = data;
    this.propagateChange(this.innerValue);
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
