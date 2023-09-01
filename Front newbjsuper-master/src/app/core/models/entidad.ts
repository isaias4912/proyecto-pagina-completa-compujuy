import { Domicilio } from "./domicilio";
import { Contacto } from "./contacto";
import { Telefono } from "./telefono";
import { Persona } from "./persona";
import { Empresa } from "./empresa";
import { Entity } from '../interfaces/ientity';

export class Entidad implements Entity {
    id: number;
    tipo: 'PERSONA' | 'EMPRESA';
    domicilios: Array<Domicilio>
    contactos: Array<Contacto>
    telefonos: Array<Telefono>
    observacion: string;
    persona: Persona;
    empresa: Empresa;
    description(): string {
        if (this.tipo == 'EMPRESA') {
            return this.empresa.razonSocial;
        }
        if (this.tipo == 'PERSONA') {
            return this.persona.apellido + ' ' + this.persona.nombre;
        }
        return '';
    }
}
