import { Domicilio } from './domicilio';
import { Contacto } from './contacto';
import { Telefono } from './telefono';

export class Empresa {
    public constructor(init?: Partial<Empresa>) {
        Object.assign(this, init);
    }
    id: number;
    nombre: string = "";
    razonSocial: string;
    cuit: string;
    tipoEmpresa: number;
    estado: boolean;
    observacion: string;
    persona: any;
    domicilios: Array<Domicilio>
    contactos: Array<Contacto>
    telefonos: Array<Telefono>
}
