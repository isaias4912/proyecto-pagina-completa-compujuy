import { Entity } from "../interfaces/ientity";
import { Entidad } from './entidad';

export class Proveedor implements Entity {
    id: number;
    estado: boolean;
    tipoProveedor: number;
    observacion: string;
    cuit: string;
    razonSocial: string;
    tipo: any;
    persona: any;
    entidad: Entidad;
    tipoDocProveedor: string;
    nroDocProveedor: string;
    description(): string {
        if (this.entidad) {
            if (this.entidad.tipo == 'EMPRESA') {
                return this.entidad.empresa.razonSocial;
            }
            if (this.entidad.tipo == 'PERSONA') {
                return this.entidad.persona.apellido + ' ' + this.entidad.persona.nombre;
            }
        }
        return '';
    }

}