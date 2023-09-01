import { Component, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'new-producto-page',
  template: '<new-producto [tipoProducto]="\'SIMPLE\'" ></new-producto>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewProdPage {

  constructor(private titleService: Title
  ) {
    this.titleService.setTitle('Nuevo producto');
  }

}

