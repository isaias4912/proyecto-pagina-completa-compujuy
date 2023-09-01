import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
@Injectable()
export class StorageService {

  constructor() {
  }

  private getWithoutKey = function (key) {
    try {
      return localStorage.getItem(key);
    } catch (e) {
      console.error("error al guardar en local storage");
    }
  };

  public setMainData(key, value) {
    let keyMain = environment.prefix + "_" + "226458993604js";
    let token = this.getWithoutKey(keyMain);
    let data = null;
    let dataTemp = [];
    if (token != null || token != undefined) {
      try {
        let base64 = token.replace('-', '+').replace('_', '/');
        data = JSON.parse(this.decodeBase64(base64));
      } catch (e) {
        data = [];
      }
    }
    if (data == null || data == undefined) {
      data = [];
    }
    // eliminamos si existe la clave para setearla
    for (let i = 0; i < data.length; i++) {
      if (data[i].key != key) {
        dataTemp.push(data[i]);
      }
    }
    dataTemp.push({ key: key, value: value });
    try {
      localStorage.setItem(keyMain, btoa(JSON.stringify(dataTemp)));
    } catch (e) {
      console.error("error al guardar en local storage");
    }
  }
  setData(value, key) {
    key = environment.prefix + "_" + key;
    try {
      localStorage.setItem(key, btoa(JSON.stringify(value)));
    } catch (e) {
      throw e;
    }
  };
  getData(key) {
    key = environment.prefix + "_" + key;
    var token = this.getWithoutKey(key);
    try {
      var base64 = token.replace('-', '+').replace('_', '/');
      return JSON.parse(this.decodeBase64(base64));
    } catch (e) {
      return null;
    }
  }
  public removeMainData(){
    let keyMain = environment.prefix + "_" + "226458993604js";
    localStorage.removeItem(keyMain);
  }
  public getMainData(key) {
    let keyMain = environment.prefix + "_" + "226458993604js";
    let token = this.getWithoutKey(keyMain);
    let value = null;
    try {
      let base64 = token.replace('-', '+').replace('_', '/');
      let data = JSON.parse(this.decodeBase64(base64));
      for (let i = 0; i < data.length; i++) {
        if (data[i].key == key) {
          value = data[i].value;
        }
      }
      return value;
    } catch (e) {
      return null;
    }
  }

  public addMainData(key, value){
    let data= this.getMainData(key);
    if (data){
      data.push(value);
    }else{
      data=[];
      data.push(value);
    }
    this.setMainData(key,data);
  }
  
  public decodeBase64(str) {
    let buffer;
    let fromCharCode = String.fromCharCode;
    let re_btou = new RegExp([
      '[\xC0-\xDF][\x80-\xBF]',
      '[\xE0-\xEF][\x80-\xBF]{2}',
      '[\xF0-\xF7][\x80-\xBF]{3}'
    ].join('|'), 'g');
    let cb_btou = function (cccc) {
      switch (cccc.length) {
        case 4:
          let cp = ((0x07 & cccc.charCodeAt(0)) << 18)
            | ((0x3f & cccc.charCodeAt(1)) << 12)
            | ((0x3f & cccc.charCodeAt(2)) << 6)
            | (0x3f & cccc.charCodeAt(3));
          let offset = cp - 0x10000;
          return (fromCharCode((offset >>> 10) + 0xD800)
            + fromCharCode((offset & 0x3FF) + 0xDC00));
        case 3:
          return fromCharCode(((0x0f & cccc.charCodeAt(0)) << 12)
            | ((0x3f & cccc.charCodeAt(1)) << 6)
            | (0x3f & cccc.charCodeAt(2)));
        default:
          return fromCharCode(((0x1f & cccc.charCodeAt(0)) << 6)
            | (0x3f & cccc.charCodeAt(1)));
      }
    };
    let btou = function (b) {
      return b.replace(re_btou, cb_btou);
    };
    let _decode = buffer ? function (a) {
      return (a.constructor === buffer.constructor
        ? a : new buffer(a, 'base64')).toString();
    }
      : function (a) {
        return btou(atob(a));
      };
    return _decode(String(str).replace(/[-_]/g, function (m0) {
      return m0 === '-' ? '+' : '/';
    })
      .replace(/[^A-Za-z0-9\+\/]/g, ''));
  }
}
