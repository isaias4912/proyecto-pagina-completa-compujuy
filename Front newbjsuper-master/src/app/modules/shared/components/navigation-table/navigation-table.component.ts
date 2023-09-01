import { Component, Input, ChangeDetectionStrategy, AfterViewInit, Output, EventEmitter }
  from '@angular/core';
import { ParamPagination } from 'src/app/core/models/param-pagination';
import { NavigationTableService } from './navigation-table.service';


@Component({
  selector: 'navigation-table',
  templateUrl: './navigation-table.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavigationTableComponent implements AfterViewInit {
  @Input()
  public nameTable: string = "";
  @Input()
  public nameBtnMain: string = "";
  @Input()
  public documentTarget: string = "";
  @Input()
  public paramPagination: ParamPagination;
  @Output()
  moreEvent = new EventEmitter<any>();
  public loading: boolean = false;

  public currentFocus: number = 0;
  public cells: NodeListOf<any>;
  constructor(
    private navigationTableService: NavigationTableService
  ) {

  }
  ngAfterViewInit(): void {
    this.makeCells();
    var modalBody = document.getElementById(this.documentTarget);
    modalBody.addEventListener('keydown', (e: any) => {
      if (e.keyCode == 40) { // move down
        this.onKeyDown(e);
      }
      if (e.keyCode == 38) { // move up
        this.onKeyUp(e);
      }
      return false;
    });
    this.navigationTableService.finishLoad$.subscribe(resp => {
      this.loading = false;
    })
    this.initFocus();
  }

  private makeCells() {
    let navTable = document.getElementById(this.nameTable);
    this.cells = null;
    this.cells = navTable.querySelectorAll('tbody tr');
    console.log('this.cells', this.cells)
    this.cells.forEach(e => {
      $(e.currentTarget).off('click');
    });
    this.cells.forEach(e => {
      $(e).click((e: any) => {
        this.currentFocus = $(e.currentTarget).data('row');
        this.setFocus(this.currentFocus);
      }); // Adds another click event
    });
  }

  public onKeyDown(event: KeyboardEvent) {
    if (!this.loading) {
      this.makeCells();
      if (this.currentFocus < (this.cells.length - 1)) {
        if (this.currentFocus == null) {
          this.currentFocus = 0;
        } else {
          this.currentFocus++;
        }
        this.setFocus(this.currentFocus);
      } else {
        let totalPages = Math.ceil((this.paramPagination.totalItems / this.paramPagination.itemsPerPage));
        let page = (this.paramPagination.page + 1);
        if (page <= totalPages) {
          this.loading = true;
          this.more();
        }
      }
    }
  }
  public more() {
    this.moreEvent.emit();
  }

  public onKeyUp(event: KeyboardEvent) {
    if (this.currentFocus > 0) {
      this.currentFocus--;
      this.setFocus(this.currentFocus);
    }
  }

  public initFocus() {
    this.currentFocus = 0;
    this.setFocus(0);
  }
  private setFocus(index: number) {
    let navTable = document.getElementById(this.nameTable);
    if (navTable != null) {
      var activeTDs = navTable.querySelectorAll('.active-row');
      for (var i = 0; i < activeTDs.length; i++) {
        activeTDs[i].classList.remove('active-row');
      }
      this.cells[index].classList.add('active-row');
      setTimeout(() => {
        $("#" + this.nameBtnMain + index).focus();
      });
    }
  }
}
