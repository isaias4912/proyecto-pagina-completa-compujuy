import { Component, ChangeDetectionStrategy, AfterViewInit, Input } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { TypeComponent } from 'src/app/core/component-page';
import { TipoSearch } from 'src/app/core/enums';
declare var $: any;
@Component({
  selector: 'search-cli-modal',
  templateUrl: './search-cli.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchCliModal extends Dialog implements AfterViewInit {

  public TipoSearch = TipoSearch;
  public data: RespPagination<any>;
  public TypeComponent = TypeComponent;
  @Input()
  public typeSearch: TipoSearch = TipoSearch.SEARCH_FILTER;
  
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }
  selectCli(cliente){
    this.activeModal.close(cliente);
  }
}

