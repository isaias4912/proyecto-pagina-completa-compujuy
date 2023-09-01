import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from 'src/app/core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { FamiliaHTTPService } from 'src/app/core/services/http/familia-http.service';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage, TypeComponent } from 'src/app/core/component-page';
import { List } from 'src/app/core/list';
import { ThreeFamModalComponent } from '../../modules/shared-fam/components/modal/three-mod/three-fam-mod.component';
declare var $: any;

@Component({
  selector: 'list-fam',
  templateUrl: './list-fam.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListFamComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public familias$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  private params: any = null;
  @Output()
  selectFam = new EventEmitter<any>();
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  public filterfamilia = {
    text: "",
    id: null
  };
  constructor(
    private familiaHTTPService: FamiliaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute

  ) {
    super();
  }

  ngOnInit() {
    this.familias$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.buildFilter(params);
        }),
          switchMap(() => this.familiaHTTPService.getAllPagination(this.paramPagination, this.filterfamilia)),
          tap((resp: RespPagination<any>) => {
            this.setValuePagination(resp);
          }), pluck('data')
        )));
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  buildFilter(params) {
    if (this.typeComponent == TypeComponent.page) {
      if (this.firstExecute) {
        this.firstExecute = false;
        this.resetFilter();
        this.filterfamilia = this.getFilterFromUrl(this.filterfamilia, params, ['id']);
      }
    }
  }
  showChartThree() {
    const modalThreeFamilias = this.modalService.open(ThreeFamModalComponent, { size: 'xl', backdrop: 'static' });
  }
  showChartFam(familia) {
    const modalThreeFamilias = this.modalService.open(ThreeFamModalComponent, { size: 'xl', backdrop: 'static' });
    modalThreeFamilias.componentInstance.familia = familia;
  }
  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterfamilia);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterfamilia);
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltro").focus();
    });
  }
  resetFilter() {
    this.filterfamilia = {
      text: "",
      id: ""
    };
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirmar eliminación  - ' + entity.nombre,
      accept: () => {
        this.familiaHTTPService.deleteFamilia(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente la familia  - ' + entity.nombre);
          this.pageChanged();
        });
      },
      cancel: () => {
      }
    });
  }
  select(entity: any) {
    this.selectFam.emit(entity);
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.id = this.encrypt(encodeURI(data.id));
      tempParam.text = encodeURI(data.text);
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

