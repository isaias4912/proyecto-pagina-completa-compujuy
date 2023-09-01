import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { ComponentPage } from 'src/app/core/component-page';
import { FormGroup, FormBuilder, Validators, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { ProveedorHTTPService } from 'src/app/core/services/http/proveedor-http.service';
import { RespPagination } from 'src/app/core/models/resp-pagination';
import { Proveedor } from 'src/app/core/models/proveedor';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Dialog } from 'src/app/core/dialog';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ParamPagination } from 'src/app/core/models/param-pagination';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
declare var $: any;
@Component({
  selector: 'search-prov',
  templateUrl: './search-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchProvComponent extends Dialog implements OnInit, AfterViewInit {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public data: RespPagination<Proveedor>;
  @Input()
  title: string = "Busqueda de proveedores"
  @Input()
  public text: string = ""
  public paramPagination: ParamPagination;
  public proveedores$: Observable<RespPagination<Proveedor>> = null;
  private refresh = new BehaviorSubject<void>(null);
  private type = 0;
  public formFilter: FormGroup;

  constructor(
    private proveedorHTTPService: ProveedorHTTPService,
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
  ) {
    super(activeModal, confirmationService);
    this.paramPagination = new ParamPagination();
  }

  ngOnInit() {
    this.formFilter = this.formBuilder.group({
      q: [null, []]
    });
    if (this.data && this.data.data.length > 0) {
      this.type = 1;
      this.proveedores$ = of(this.data).pipe(tap(resp => {
        this.setValuePagination(resp);
      }));
    } else {
      this.type = 0;
      this.setDefaultHttp();
    }
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      $("#txtFiltro").focus();
    });
    this.formFilter.patchValue({ q: this.text });
  }

  pageChanged() {
    if (this.type == 1) {
      this.type = 0;
      this.setDefaultHttp();
    }
    this.refresh.next();

  }

  private setDefaultHttp() {
    this.proveedores$ = this.refresh.asObservable().pipe(
      switchMap(() => this.proveedorHTTPService.getProveedoresByMultipleFilter(this.formFilter.get('q').value, "id", this.paramPagination.page, 10, true))
      , tap((resp: RespPagination<Proveedor>) => {
        this.cdr.markForCheck();
        this.setPagination(resp);
      })
    );
  }
  public detailProveedor(data){

  }
  selectProveedor(proveedor: Proveedor) {
    this.confirmation.selectEvent.emit(proveedor);
    this.activeModal.close();
  }
}

