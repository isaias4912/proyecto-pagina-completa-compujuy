import { Component, ChangeDetectionStrategy } from '@angular/core';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'update-producto-page',
  template: '<update-producto [tipoProducto]="\'COMPUESTO\'" ></update-producto>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpdateProdCompPage {

  constructor(private titleService: Title
  ) {
    this.titleService.setTitle('Modificación de producto');
  }

}

