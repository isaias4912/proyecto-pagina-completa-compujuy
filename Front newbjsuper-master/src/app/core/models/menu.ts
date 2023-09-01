import { MenuItem } from "./menu-item";

export class Menu {
    descripcion :string;
    id: number;
    nombre: string;
    rol:string;
    menuItems: Array<MenuItem>;
}
