import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, ViewChildren, QueryList, OnDestroy, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';
import { pluck, tap, take } from 'rxjs/operators';
import 'jquery-colorbox';
import { Dialog } from 'src/app/core/dialog';
import { Producto } from 'src/app/core/models/producto';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { PathService } from 'src/app/core/services/utils/path.service';
declare var $: any;

@Component({
  selector: 'detail-product',
  templateUrl: './detail-product.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailProductoComponent extends Dialog implements OnInit, AfterViewInit, OnDestroy {
  @Input()
  public idEntity: number;
  producto: Producto;
  producto$: Observable<any>;
  @ViewChildren('imgProducts') imgProducts: QueryList<any>;
  private subsProductos: Subscription;

  constructor(
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
    private productosHTTPService: ProductosHTTPService,
    public pathService: PathService
  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
    this.producto$ = this.productosHTTPService.getProductoById(this.idEntity, 0).pipe(take(1), pluck('data'),
      tap(resp => {
        this.producto = resp;
      })
    );
  }

  getProducto(){
    return this.producto;
  }

  ngAfterViewInit(): void {
    let overflow;
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
    this.subsProductos = this.imgProducts.changes.subscribe(t => {
      $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
    })
  }
  
  ngOnDestroy(): void {
    this.subsProductos.unsubscribe();
  }
}

