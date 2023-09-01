import { Component, ChangeDetectionStrategy } from '@angular/core';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'new-producto-comp-page',
  template: '<new-producto [tipoProducto]="\'COMPUESTO\'" ></new-producto>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewProdCompPage {

  constructor(private titleService: Title
  ) {
    this.titleService.setTitle('Nuevo producto');
  }

}

