import { Proveedor } from './proveedor';
import { DetMovimiento } from './acc-det-mov';

export class EncMovimiento {
    public constructor(init?: Partial<EncMovimiento>) {
        Object.assign(this, init);
    }
    id: number;
    fechaCarga: string;
    fechaFactura: string;
    numero: string;
    activo: boolean;
    motivoAnulacion: string;
    tipo: number;
    cargada: number;
    tipoFactura: number;
    subtotal: number;
    total: number;
    completa: number;
    idProveedor: number;
    idSucursal: number;
    totalDescuentos: number;
    totalAdicionales: number;
    totalImpuestos: number;
    totalIVAs: number;
    totalBaseImp: number;
    proveedor: Proveedor;
    pagada: boolean;
    tipoPago: number;
    movimientosCtaCteProv : Array<DetMovimiento>;
    impuestos : Array<DetMovimiento>;
    descuentos : Array<DetMovimiento>;
    adicionales : Array<DetMovimiento>;
    items : Array<any>;
    detalleTransaccion : any;
}
