import { ProductoPadre } from './producto-padre';
import { ImagenProducto } from './imagen-producto';
import { EncMovimiento } from './acc-enc-mov';

export class DetMovimiento {
    public constructor(init?: Partial<DetMovimiento>) {
        Object.assign(this, init);
    }
    id: number;
    tipo: number;
    numero;
    monto: number;
    montoTotal: number;
    saldo: number;
    interes: number;
    fecha: string;
    pagado: boolean;
    descripcion: string;
    pagosCtaCte:[];
    factura: EncMovimiento ;
}
