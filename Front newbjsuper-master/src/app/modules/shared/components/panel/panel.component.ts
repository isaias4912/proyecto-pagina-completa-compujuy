import { Component, OnInit, Input, ChangeDetectionStrategy, AfterViewInit, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
declare var $: any;

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PanelComponent implements OnInit, AfterViewInit {

  @Input()
  public idCollapse: string = null;
  @Input()
  public title: string = "Filtro de busqueda";
  public bandera: any;
  @Input()
  public showHeader: boolean = true;
  @Input()
  public iconFilter: boolean = true;
  @Input()
  public show: boolean = true;
  @Input()
  public collapse: boolean = true;
  @Input()
  public footer: boolean = false;
  @Input()
  public tooltip: string = null;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  finishLoad = new EventEmitter<null>();
  constructor(
  ) {

  }

  ngOnInit() {
    this.bandera = !this.show;
    if (this.showHeader === null) {
      throw new Error("Attribute 'idCollapse' is required");
    }
  }

  ngAfterViewInit(): void {
    this.loadHelp();
    this.finishLoad.emit();
  }
  public loadHelp() {
    // cargamos la ayuda
    $('.'+this.name+'-help-ns').hide();
    $('.label-help').hover(
      function () {
        $(this).find('.'+this.name+'-help-ns').show();
      }, function () {
        $(this).find('.'+this.name+'-help-ns').hide();
      }
    );
    $('.'+this.name+'-help-ns').tooltipster({
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      contentAsHTML: true,
      interactive: true,
      minWidth: 250,
      maxWidth: 350,
    });
  }
}
