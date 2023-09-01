import { Component, OnInit, Input, ChangeDetectionStrategy, AfterViewInit, ChangeDetectorRef, Output, EventEmitter }
  from '@angular/core';
import { Observable } from 'rxjs';
import { DataCount } from 'src/app/core/interfaces/idataCount';

@Component({
  selector: 'button-footer',
  templateUrl: './button-footer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonFooterComponent implements OnInit, AfterViewInit {
  @Input()
  public saveAndReturn:boolean=true;
  @Input()
  public saveAndNew:boolean=true;
  @Input()
  public save:boolean=true;
  @Input()
  service: Observable<any>;
  @Input()
  dataCount: DataCount;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  prefixFileName: string = "file";
  @Input()
  typePrint: string = null;
  @Output()
  clickSave = new EventEmitter<void>();
  @Output()
  clickSaveAndReturn = new EventEmitter<void>();
  @Output()
  clickSaveAndNew = new EventEmitter<void>();
  @Output()
  clickCancel = new EventEmitter<void>();
  constructor(
    private cdr: ChangeDetectorRef
  ) {

  }
  ngAfterViewInit(): void {
  }

  ngOnInit() {
    if(this.save){

    }
  }
  onSave() {
    this.clickSave.emit();
  };
  onSaveAndNew() {
    this.clickSaveAndNew.emit();
  };
  onSaveAndReturn() {
    this.clickSaveAndReturn.emit();
  };
  onCancel() {
    this.clickCancel.emit();
  }

}
