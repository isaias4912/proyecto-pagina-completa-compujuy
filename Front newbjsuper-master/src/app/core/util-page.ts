import { Output, EventEmitter, Component } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
declare var $: any;

@Component({
  template: ''
})
export abstract class UtilPage {
  protected contentLoadPreview: string = '<div class="form-group" >'
    + '<div class="col-md-12 text-bold" >Cargando... <img src="assets/images/ajax-mini.gif" /></div>'
    + '</div>';
  public view: any = [];
  @Output()
  finishViewEvent = new EventEmitter<any>();
  finishView: boolean = false;
  public sizeWindow: number;

  constructor() {
    this.setCantView();
  }
  public setCantView(cant: number = 1) {
    this.view = []
    for (let i = 0; i < cant; i++) {
      this.view[i] = false;
    }
  }
  public encrypt(value) {
    return this.b64EncodeUnicode(value);
  }
  public decrypt(value) {
    if (value) {
      try {
        return this.b64DecodeUnicode(value);
      } catch (e) {
        console.error(e);
        return null;
      }
    } else {
      return null;
    }
  }
  private b64EncodeUnicode(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function (match, p1) {
      return String.fromCharCode(parseInt(p1, 16))
    }))
  }
  private b64DecodeUnicode(str) {
    try {
      return decodeURIComponent(Array.prototype.map.call(atob(str), function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
      }).join(''))
    } catch (e) {
      console.error(e);
      return null;
    }
  }
  public formatDecimal(value) {
    if (value != null) {
      let res = value.toString().split(".");
      if (res.length == 1 || res[1].length < 3) {
        value = value.toFixed(2);
      }
    }
    return value;
  }
  public loadingFinishView(id: number): boolean {
    if (id) {
      let value = this.updateFinishView(id);
      if (value) {
        this.finishViewEvent.emit();
      }
      return value;
    }
  }
  public updateFinishView(id: number): boolean {
    this.view[(id - 1)] = true;
    let value = true;
    for (let i = 0; i < this.view.length; i++) {
      if (!this.view[i]) {
        value = false;
      }
    }
    return value;
  }
  public isFinishView(): boolean {
    let value = true;
    for (let i = 0; i < this.view.length; i++) {
      if (!this.view[i]) {
        value = false;
      }
    }
    return value;
  }
  public round2(val) {
    return Math.round(val * 100) / 100;
    // return val;
  }
  /**
   * 
   * @param data Retorna objetos complejos con los datos necesarios, gralmente id y nombre
   * @param first 
   * @param second 
   */
  public getMinData(data: any, first: string = 'id', second: string = 'nombre') {
    let temp: any = {};
    temp[first] = data[first];
    temp[second] = data[second];
    return temp;
  }
  public loadHelp(clazz: string = "label-help") {
    // cargamos la ayuda
    // try{
    //   $('.help-ns').tooltipster('destroy');
    // }catch(e){}
    $(".help-ns").hide();
    $('.' + clazz).hover(
      function () {
        $(this).find(".help-ns").show();
      }, function () {
        $(this).find(".help-ns").hide();
      }
    );
    $('.help-ns:not(".tooltipstered")').tooltipster({
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      contentAsHTML: true,
      interactive: true,
      minWidth: 250,
      maxWidth: 350,
    });
  }
  public loadHelpByClass(clazz: string) {
    // cargamos la ayuda
    $('.' + clazz).tooltipster({
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      contentAsHTML: true,
      interactive: true,
      minWidth: 250,
      maxWidth: 350,
    });
  }
  public getFormGroup(control: AbstractControl) {
    return control as FormGroup;
  }
}
