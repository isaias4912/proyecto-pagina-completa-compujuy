import { Component, OnInit, ViewChild, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { IDataForm } from '../../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../../core/component-page';
import { ClienteHTTPService } from '../../../../../../../core/services/http/cliente-http.service';
import { Cliente } from 'src/app/core/models/cliente';
import { FieldsCtaCteComponent } from '../fields/fields-cta-cte.component';
declare var $: any;

@Component({
  selector: 'new-cta-cte',
  templateUrl: './new-cta-cte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewCtaCteComponent extends ComponentPage implements OnInit {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCtaCteComponent, { static: false })
  fieldsCtaCteComponent: FieldsCtaCteComponent;
  @Input()
  cliente: Cliente = null;
  @Input()
  public buttonAction: boolean = true;
  @Output()
  eventSave = new EventEmitter<any>();
  constructor(
    private clienteHTTPService: ClienteHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsCtaCteComponent.formCtaCte.valid) {
      this.clienteHTTPService.createCtaCte(this.cliente.id, this.fieldsCtaCteComponent.formCtaCte.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente la cuenta corriente");
        if (option == 1) {
          this.fieldsCtaCteComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
        this.eventSave.emit(resp)
      });
    }
  }
}

