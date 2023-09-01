import { Entidad } from "./entidad";
import { Entity } from "../interfaces/ientity";

export class Cliente implements Entity {
    id: number;
    estado: boolean;
    tipoCliente: number;
    observacion: string;
    nombreCompleto: string;
    entidad: Entidad;
    tipoDocCliente: string;
    nroDocCliente: string;
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
