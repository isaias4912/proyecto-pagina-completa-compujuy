import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Dialog } from '../../../../core/dialog';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'src/environments/environment';

declare var $: any;

@Component({
  selector: 'send-mail-modal',
  templateUrl: './send-mail-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SendMailModalComponent extends Dialog implements OnInit, AfterViewInit {

  nameSel2: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  data: any = [];
  private query2: string = "";
  @Input()
  title: string = "";
  @Input()
  mails: [] = [];
  public formEmail: FormGroup;

  constructor(
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder
  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.formEmail = this.formBuilder.group({
      email: ['', []]
    });

  }
  ngAfterViewInit(): void {
    this.buildData();
    this.load();
    $('#' + this.nameSel2).select2("open");
  }
  buildData() {
    this.data = [];
    if (this.mails && this.mails.length > 0) {
      this.mails.forEach((item: any) => {
        let email: any = {
          id: item.id,
          text: item.descripcion,
          email: item.descripcion,
          selected: true
        }
        this.data.push(email);
      })
    }
  }
  load() {
    $('#' + this.nameSel2).select2({
      theme: 'bootstrap4',
      width: '100%',
      data: this.data,
      ajax: {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('access_token')
        },
        url: environment.baseAPIURL + "entidad/all/emails",
        dataType: 'json',
        data: (params) => {
          this.query2 = params.term;
          var query = {
            q: params.term
          }
          return query;
        },
        processResults: (data) => {
          if (data.data.length <= 0) {
            data.data.push({ id: this.query2, email: this.query2, nombre: "", nuevo: true });
          }
          return {
            results: data.data
          };
        },
        cache: true
      },
      placeholder: 'Busque el email',
      language: this.languaje,
      minimumInputLength: 2,
      templateResult: function (data) {
        if (data.loading) {
          return data.text;
        } else {
          if (data.nuevo) {
            return data.email + "-<b style='color:white;'>&nbsp; NUEVO!</b>";
          } else {
            return data.email + " - " + data.nombre;
          }
        }
      },
      templateSelection: function (data) {
        if (data.id == "") {
          return data.text;
        } else {
          return data.email;
        }
      },
      escapeMarkup: function (m) {
        return m;
      }
    });
    $('#' + this.nameSel2).on("select2:selecting", (e) => {
      var data = e.params.args.data;
      if (!this.isEmail(data.email)) {
        this.messageToast.error("Email con formato incorrecto.");
        e.preventDefault();
      }
    });
  }

  confirmar() {
    let data = this.getData();
    if (data && data.length > 0) {
      this.confirmation.acceptEvent.emit(this.getData());
    } else {
      this.messageToast.error("Debe seleccionar al menos un email!");
    }
  }
  public getData(): [] {
    let tempEmail = $('#' + this.nameSel2).select2('data');
    let emails: any;
    emails = [];
    if (tempEmail) {
      tempEmail.forEach(element => {
        emails.push({ id: element.id, email: element.email });
      });
    }
    return emails;
  }

  isEmail(search: string): boolean {
    let serchfind: boolean;
    let regexp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    serchfind = regexp.test(search);
    return serchfind
  }
  cancel() {

  }

}

