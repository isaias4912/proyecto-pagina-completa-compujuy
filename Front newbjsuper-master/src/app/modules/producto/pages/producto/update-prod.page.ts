import { Component, ChangeDetectionStrategy } from '@angular/core';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'update-producto-page',
  template: '<update-producto [tipoProducto]="\'SIMPLE\'" ></update-producto>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpdateProdPage {

  constructor(private titleService: Title
  ) {
    this.titleService.setTitle('Modificación de producto');
  }

}

