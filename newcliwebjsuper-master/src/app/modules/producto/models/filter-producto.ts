class CodigoBarras {
    codigo: string = ""
    constructor(codigo: string) {
        this.codigo = codigo;
    }
}
class ProductoPadre {
    nombre: string = "";
}
class Producto {
    nombre: string = "";
    codigoEspecial: string = "";
    id: number = 0;
    codigoBarras: Array<CodigoBarras> = new Array<CodigoBarras>(new CodigoBarras(""));
    productoPadre: ProductoPadre = new ProductoPadre();
}
export class FilterProducto {
    producto: Producto = new Producto();
    filterStock: number = 0;
    showStock: boolean = false;
    extraFilter: boolean = false;
    familias: [] = [];
    sucursales: [] =[];
    activo: number = 1;
    tipo: number = 1 // por defecto productos simples
}
