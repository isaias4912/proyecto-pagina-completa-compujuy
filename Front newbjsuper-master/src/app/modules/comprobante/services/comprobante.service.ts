import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class ComprobanteService {
    private subtotal = new BehaviorSubject<number>(0);
    private total = new BehaviorSubject<number>(0);
    private totalImpuestos = new BehaviorSubject<number>(0);
    private totalBaseImponible = new BehaviorSubject<number>(0);
    private totalTributos = new BehaviorSubject<number>(0);
    private listaPrecio = new BehaviorSubject<number>(null);

    constructor() { }

    subtotalChange$ = this.subtotal.asObservable();
    totalImpuestosChange$ = this.totalImpuestos.asObservable();
    totalBaseImponiblleChange$ = this.totalBaseImponible.asObservable();
    totalChange$ = this.total.asObservable();
    totalTributos$ = this.totalTributos.asObservable();
    listaPrecio$ = this.listaPrecio.asObservable();

    changeSubtotal(mensaje) {
        this.subtotal.next(mensaje);
    }
    changeTotal(mensaje) {
        this.total.next(mensaje);
    }
    changeTotalImpuestos(mensaje) {
        this.totalImpuestos.next(mensaje);
    }
    changeTotalBaseImponible(mensaje) {
        this.totalBaseImponible.next(mensaje);
    }
    changeTotalTributos(mensaje) {
        this.totalTributos.next(mensaje);
    }
    changeListaPrecio(mensaje) {
        this.listaPrecio.next(mensaje);
    }

}