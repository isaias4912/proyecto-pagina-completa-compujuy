import { Component, OnInit, ViewChild, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { IDataForm } from '../../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../../core/component-page';
// import { FieldsProdPadreComponent } from '../fields/fields-cta-cte.component';
import { ClienteHTTPService } from '../../../../../../../core/services/http/cliente-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { FieldsCtaCteComponent } from '../fields/fields-cta-cte.component';
import { Cliente } from 'src/app/core/models/cliente';
declare var $: any;

@Component({
  selector: 'update-cta-cte',
  templateUrl: './update-cta-cte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateCtaCteComponent extends ComponentPage implements OnInit {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCtaCteComponent, { static: false })
  fieldsCtaCteComponent: FieldsCtaCteComponent;
  public ctaCte$: Observable<any>;
  @Input()
  public idCtaCte: number;
  @Input()
  cliente: Cliente = null;
  @Input()
  public buttonAction: boolean = true;
  @Output()
  eventSave = new EventEmitter<any>();
  constructor(
    private clienteHTTPService: ClienteHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    if (this.idCtaCte) {
      this.id = this.idCtaCte;
      this.ctaCte$ = this.clienteHTTPService.getCtaCte(this.id).pipe(pluck('data'));
    } else {
      this.activatedRoute.paramMap.subscribe(
        (params: ParamMap) => {
          this.id = this.decrypt(params.get("idCtaCte"));
          if (this.id) {
            this.ctaCte$ = this.clienteHTTPService.getCtaCte(this.id).pipe(pluck('data'));
          }
        });
    }
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsCtaCteComponent.formCtaCte.valid) {
      this.clienteHTTPService.updateCtaCte(this.id, this.fieldsCtaCteComponent.formCtaCte.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente la cuenta corriente");
        if (option == 1) {
          this.fieldsCtaCteComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
        this.eventSave.emit(resp)
      });
    }
  }
}

