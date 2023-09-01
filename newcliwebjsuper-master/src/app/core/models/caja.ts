import { Sucursal } from "./sucursal";

export class Caja {
    id: number;
    nombre: string;
    ipMaquina: string;
    nombreMaquina: string;
    observacion: string;
    activo: boolean;
    modificaPrecio: boolean;
    modificaDescuento: boolean;
    modificaAdicional: boolean;
    limiteConsumidorFinal: number;
    conControlEstricto: boolean;;
    sucursales: Array<Sucursal>;
    idPuntoVenta: number;
    comprobantesHab: string;;
}
