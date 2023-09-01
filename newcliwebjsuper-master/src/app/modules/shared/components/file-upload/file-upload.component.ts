import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { IDataImage } from '../../../../core/models/idata-image';
import { HttpEventType } from '@angular/common/http';
import { MainHTTPService } from '../../../../core/services/http/main-http';
import { Subscription } from 'rxjs';
declare var $: any;

@Component({
  selector: 'file-upload',
  templateUrl: 'file-upload.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileUploadComponent implements OnInit, AfterViewInit {


  @Input()
  private fileType:any;
  @Input()
  path: string = '';
  @Input()
  dataImage: IDataImage;
  @Input()
  target: string='addFile';
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  loadFile = new EventEmitter<{ target: string, data: any }>();
  @Output()
  progressUpFile = new EventEmitter<{ value: number }>();
  private subscriptionImgUp: Subscription;
  constructor(
    private message: ToastrService,
    private mainHTTPService: MainHTTPService,
  ) { }

  ngOnInit() {
    if (this.fileType == "crt" || this.fileType == "cert") {
      this.fileType = /(\.|\/)(crt|cert)$/i;
    }
  }

  resetFile() {
    $('#progress .progress-bar').css('width', '0%');
    $('#files').html("");
  };
  cancelUploadIMage() {
    this.subscriptionImgUp.unsubscribe();
  };
  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      let formData: FormData = new FormData();
      formData.append('files[]', file);
      this.subscriptionImgUp = this.mainHTTPService.postMultipart(environment.baseAPIURL + this.path, formData).subscribe(resp => {
        // se envia un listado de imagenes pero tomamos una nomas
        if (resp.type === HttpEventType.UploadProgress) {
          const percentDone = Math.round(100 * resp.loaded / resp.total);
          this.dataImage = { ...this.dataImage, showProgress: true }
          this.progressUpFile.emit({ value: percentDone });
        }
        if (resp.type === HttpEventType.Response) {
          let messageEmit = { target: this.target, data: resp.body };
          this.loadFile.emit(messageEmit);
        }
      });
    }
  }

  ngAfterViewInit(): void {
  }


}
