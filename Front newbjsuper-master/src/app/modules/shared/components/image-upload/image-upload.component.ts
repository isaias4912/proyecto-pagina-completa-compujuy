import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { IDataImage } from '../../../../core/models/idata-image';
import { HttpEventType } from '@angular/common/http';
import { MainHTTPService } from 'src/app/core/services/http/main-http';
import { Subscription } from 'rxjs';
declare var $: any;

@Component({
  selector: 'image-upload',
  templateUrl: 'image-upload.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImageUploadComponent implements OnInit, AfterViewInit {


  private filetype = /(\.|\/)(gif|jpe?g|png|PNG)$/i;
  @Input()
  path: string = '';
  @Input()
  dataImage: IDataImage;
  @Input()
  target: string='addImage';
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  loadImage = new EventEmitter<{ target: string, data: any }>();
  @Output()
  progressUpImage = new EventEmitter<{ value: number }>();
  private subscriptionImgUp: Subscription;
  constructor(
    private message: ToastrService,
    private mainHTTPService: MainHTTPService,
  ) { }

  ngOnInit() {
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
          this.progressUpImage.emit({ value: percentDone });
        }
        if (resp.type === HttpEventType.Response) {
          let messageEmit = { target: this.target, data: resp.body };
          this.loadImage.emit(messageEmit);
        }
      });
    }
  }

  ngAfterViewInit(): void {
  }


}
