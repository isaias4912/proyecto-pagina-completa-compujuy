import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ViewChild, ElementRef, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import 'select2';
import { environment } from '../../../../../../../environments/environment';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SearchProdPadreModal } from '../../modal/search/search-prod-padre.modal';
declare var $: any;

@Component({
  selector: 'select-product-parent',
  templateUrl: './select-product-parent.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectProductParentComponent implements OnInit, AfterViewInit {

  @Input()
  data: any;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  constructor(private modalService: NgbModal
  ) { }
  @ViewChild('buttonClickSelect2', { static: false }) buttonSelect2: ElementRef<HTMLElement>;
  @Output()
  selectValue = new EventEmitter<any>(false);
  event: any;
  private languaje = {
    inputTooShort: function () {
      return "Busque el producto padre...";
    },
    searching: function () {
      return "Buscando…";
    },
    noResults: function () {
      return "No se encontraron resultados";
    }
  };
  ngOnInit() {
  }
  ngAfterViewInit(): void {
    this.load();
  }
  private load() {
    $('#' + this.name).select2({
      theme: 'bootstrap4',
      // width: '100%', se lo saca por el inptu group
      data: this.data,
      ajax: {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('access_token')
        },
        url: environment.baseAPIURL + "productos/padre",
        dataType: 'json',
        data: (params) => {
          var query = {
            name: params.term
          }
          return query;
        },
        processResults: (data) => {
          if (data.data.length <= 0) {
            let text: string = $('#' + this.name).data('select2').dropdown.$search.val();
            data.data.push({ id: text, nombre: text, nombreCorto: text, nuevo: true });
          }
          return {
            results: data.data
          };
        },
        cache: true
      },
      placeholder: 'Busque el producto padre',
      language: this.languaje,
      minimumInputLength: 2,
      templateResult: (prodPadre) => {
        let markup: any;
        if (prodPadre.loading) {
          return "Buscando el producto padre...";
        }
        if (prodPadre.nuevo) {
          markup = prodPadre.nombre + "-<b style='color:white;'>&nbsp; NUEVO!</b>";
        } else {
          markup = "<div><div class='form-group' style='height: 12px; font-size: 16px; margin-bottom:10px;' >" +
            prodPadre.nombre +
            "</div>";
          if (prodPadre.familia != null) {
            markup += "<div class='form-group' style='height: 10px; font-size: 12px; margin-bottom:5px;' >" +
              prodPadre.familia.nombre + " - " + prodPadre.familia.nombreCorto +
              "</div>";
          }
          markup += "</div>";
        }
        return markup;
      },
      templateSelection: (data) => {
        let text: any;
        $(data.element).attr('data-custom-attribute', data);
        if (data.nuevo) {
          text = data.nombre + "-<b style='color:red;'>&nbsp; NUEVO!</b>";
        } else {
          text = data.nombre;
        }
        return text;
      },
      escapeMarkup: (m) => {
        return m;
      }
    });

    $('#' + this.name).on("select2:select", (e) => {
      this.event = e;
      this.buttonSelect2.nativeElement.click();
    });
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
  public setData(data: any) {
    this.data = data;
    $('#' + this.name).find('option').remove();
    $('#' + this.name).select2('destroy');
    this.load()
  }
  public clear() {
    $('#' + this.name).find('option').remove();
    $('#' + this.name).val(null).trigger('change');
  }
  showSearchProdPadre() {
    const modalSearchProdPadre = this.modalService.open(SearchProdPadreModal, { size: 'xl', backdrop: 'static' });
    modalSearchProdPadre.result.then((data) => {
      this.setData([data]);
      this.selectValue.emit(data);
    }, (reason) => {
     
    });
  }
  selectValueSelect() {
    if (this.event) {
      this.selectValue.emit(this.event.params.data);
    }
  }
}
