import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, ViewChild } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { IDataImage } from '../../../../core/models/idata-image';
import { IImage } from '../../../../core/models/iimage';
declare var $: any;

@Component({
  selector: 'image-preview',
  templateUrl: 'image-preview.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImagePreviewComponent implements OnInit, AfterViewInit {


  @Input()
  image: IImage;
  @Input()
  size: string = null;
  @Input()
  path: string;
  @Input()
  width: number = 150;
  @Input()
  height: number = 150;
  @Input()
  text: boolean = true;
  @Input()
  tags: boolean = true;
  @Input()
  colorbox: boolean = true;
  @Input()
  link: boolean = true;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  public fullPath: string = null;
  constructor(
    private message: ToastrService,
  ) { }

  ngOnInit() {
    this.loadImage(null);
  }

  public loadImage(image: IImage) {
    if (image) {
      this.image = image;
    }
    if (this.size && this.image) {
      let dataImg = this.image.nombre.split('.')
      this.fullPath = this.path + dataImg[0] + "_" + this.size + "." + dataImg[1];
    } else {
      this.fullPath = this.path + this.image.nombre;
    }
    console.log('this.fullPath', this.fullPath)
  }

  ngAfterViewInit(): void {
    let overflow;
    if (this.colorbox) {
      let colorbox_params = {
        rel: 'colorbox',
        reposition: true,
        scalePhotos: true,
        scrolling: false,
        previous: '<i class="ace-icon fa fa-arrow-left"></i>',
        next: '<i class="ace-icon fa fa-arrow-right"></i>',
        close: '&times;',
        current: '{current} of {total}',
        maxWidth: '100%',
        maxHeight: '100%',
        onOpen: function () {
          overflow = document.body.style.overflow;
          document.body.style.overflow = 'hidden';
        },
        onClosed: function () {
          document.body.style.overflow = overflow;
        },
        onComplete: function () {
          $.colorbox.resize();
        }

      };
      if (this.image.tipo && this.image.tipo != '0') {
        $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
      }
    }
  }

}
