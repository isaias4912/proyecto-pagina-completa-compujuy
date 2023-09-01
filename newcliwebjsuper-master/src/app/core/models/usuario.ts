import { Rol } from "./rol";
import { Persona } from "./persona";
import { Sucursal } from "./sucursal";

export class Usuario {
    id: number;
    usuario: string;
    password: string;
    logo: string;
    tipo: string;
    observacion: string;
    mail: string;
    tipoUsuario: number;
    idRedSocial: string;
    estado: boolean;
    altLogo: string;
    tipoLogo: string;
    keyGravatar: string;
    persona: Persona;
    roles: Array<Rol>;
    sucursales: Array<Sucursal>;
}
