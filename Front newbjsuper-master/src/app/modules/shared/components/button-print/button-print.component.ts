import { Component, OnInit, Input, ChangeDetectionStrategy, AfterViewInit, ChangeDetectorRef }
  from '@angular/core';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import * as download from 'downloadjs';
import { DataCount } from 'src/app/core/interfaces/idataCount';

@Component({
  selector: 'button-print',
  templateUrl: './button-print.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonPrintComponent implements OnInit, AfterViewInit {
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
  constructor(
    private cdr: ChangeDetectorRef
  ) {

  }
  ngAfterViewInit(): void {
  }

  ngOnInit() {

  }
  print() {
    this.service.subscribe((response: any) => {
      let nameFile = this.prefixFileName + "-" + moment().format('DMMYYYYhmmss');
      let blob = new Blob([response], { type: "application/" + this.typePrint });
      download(blob, nameFile + "." + this.typePrint, "file/" + this.typePrint);
    });
  };

}
