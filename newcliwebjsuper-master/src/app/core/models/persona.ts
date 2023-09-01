import { Domicilio } from "./domicilio";
import { Contacto } from "./contacto";
import { Telefono } from "./telefono";

export class Persona {
    id: number;
    nombre: string;
    apellido: string;
    dni: string;
    cuil: string;
    direccion: string;
    ciudad: string;
    sexo: number;
    fechAlta: string;
    fechaNac: string;
    domicilios: Array<Domicilio>
    contactos: Array<Contacto>
    telefonos: Array<Telefono>
}
