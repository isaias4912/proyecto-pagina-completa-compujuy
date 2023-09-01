import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'producto',
  templateUrl: './producto.component.html',
})
export class ProductoComponent {
  constructor(
    private activatedRoute: ActivatedRoute
  ) {
  }
}

