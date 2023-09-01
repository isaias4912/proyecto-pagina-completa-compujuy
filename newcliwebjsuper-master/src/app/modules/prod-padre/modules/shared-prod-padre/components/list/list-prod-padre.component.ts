import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { ProductoPadreHTTPService } from 'src/app/core/services/http/producto-padre-http.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from 'src/app/core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { List } from '../../../../../../core/list';
import { ActivatedRoute } from '@angular/router';
import { SelectFamilyComponent } from 'src/app/modules/familia/components/select/select-family.component';
declare var $: any;

@Component({
  selector: 'list-prod-padre',
  templateUrl: './list-prod-padre.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListProdPadreComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  @Output()
  selectProdPadre = new EventEmitter<any>();
  public productosPadre$: Observable<any> = null;
  public refresh = new BehaviorSubject<void>(null);
  @ViewChild(SelectFamilyComponent, { static: false }) selectFamilyComponent: SelectFamilyComponent
  public dataFilter = {
    productoPadre: {
      nombre: "",
      id: 0
    },
    familias: []
  };
  constructor(
    private productoPadreHTTPService: ProductoPadreHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.productosPadre$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.buildFilter(params);
        }),
          switchMap(() => this.productoPadreHTTPService.getProductosPadresByPagination(this.paramPagination, this.dataFilter))
          , tap((resp: RespPagination<any>) => {
            this.setValuePagination(resp);
          }), pluck('data')
        ),
      ));
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  buildFilter(params) {
    if (this.typeComponent == TypeComponent.page) {
      if (this.firstExecute) {
        this.firstExecute = false;
        this.resetFilter();
        if (params.id) {
          this.dataFilter.productoPadre.id = parseInt(this.decrypt(params.id));
        }
        if (params.nombre) {
          this.dataFilter.productoPadre.nombre = decodeURI(params.nombre);
        }
        if (params.familias) {
          let tempFamilias = [].concat(params.familias);
          let tempFam: any = [];
          tempFamilias.forEach(fam => {
            tempFam.push({ id: parseInt(fam) })
          });
          this.dataFilter.familias = tempFam;
        }
      }
    }
  }
  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.dataFilter);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.dataFilter)
  }
  resetFilter() {
    this.dataFilter = {
      productoPadre: {
        nombre: "",
        id: 0
      },
      familias: []
    };
    if (this.selectFamilyComponent) {
      this.selectFamilyComponent.clear();
    }
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  remove(prodPadre: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación del producto padre - ' + prodPadre.nombre,
      accept: () => {
        this.productoPadreHTTPService.deleteProductoPadre(prodPadre.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el producto padre - ' + prodPadre.nombre);
          this.pageChanged();
        });
      },
      cancel: () => {
      }
    });
  }
  select(entity: any) {
    this.selectProdPadre.emit(entity);
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.id = this.encrypt(encodeURI(data.productoPadre.id));
      tempParam.nombre = encodeURI(data.productoPadre.nombre);
      tempParam.familias = this.getParamURLFromArray(data.familias);
      this.setParamURL(tempParam).then(resp => {
        if (!resp) {
          this.refresh.next();
        }
      });
    } else {
      this.refresh.next();
    }
  }
}

