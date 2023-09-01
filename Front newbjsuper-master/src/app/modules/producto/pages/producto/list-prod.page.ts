import { Component, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'productos-list-page',
  template: '<productos-list></productos-list>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListProdPage {

  constructor(private titleService: Title
  ) {
    this.titleService.setTitle('Lista de productos');
  }

}

