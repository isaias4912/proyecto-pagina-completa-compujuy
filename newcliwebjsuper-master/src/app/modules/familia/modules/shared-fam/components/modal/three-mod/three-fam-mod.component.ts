import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, switchMap } from 'rxjs/operators';
import { Dialog } from '../../../../../../../core/dialog';
import { FamiliaHTTPService } from '../../../../../../../core/services/http/familia-http.service';
import { Familia } from '../../../../../../../core/models/familia';
import { Response } from '../../../../../../../core/services/utils/response';
import { ConfirmationServiceService } from '../../../../../../shared/services/confirmation-service.service';


@Component({
  selector: 'three-fam-modal',
  templateUrl: './three-fam-mod.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ThreeFamModalComponent extends Dialog implements OnInit, AfterViewInit {
  public filterMovimiento: any = {
    ventas: false,
    asociados: false,
    sucursales: []
  };
  public threes$: Observable<Response<any>>
  public refresh = new BehaviorSubject<void>(null);
  public dataParentChild: any = null;
  public familia: any = null;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private familiaHTTPService: FamiliaHTTPService,
    private modalService: NgbModal,

  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.threes$ = this.refresh.asObservable().pipe(switchMap(() => this.familiaHTTPService.getAllArbol().pipe(
      tap(resp => {
        this.dataParentChild = { ...resp.data };
      })
    )));
  }
  ngAfterViewInit(): void {
    this.title = 'Árbol de familias';
  }

  selectedNode(data) {
    this.activeModal.close();
    let fam = new Familia();
    fam.id = data.id;
    fam.nombre = data.label;
    fam.nivel = data.nivel;
    this.confirmation.selectEvent.emit(fam);
  }

}

