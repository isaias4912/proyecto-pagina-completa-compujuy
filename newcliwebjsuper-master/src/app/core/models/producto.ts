import { ProductoPadre } from './producto-padre';
import { ImagenProducto } from './imagen-producto';

export class Producto {
    public constructor(init?: Partial<Producto>) {
        Object.assign(this, init);
    }
    id: number;
    nombre: string = "";
    codigoEspecial: string = "";
    nombreFinal: string = "";
    productoPadre: ProductoPadre = new ProductoPadre();
    precioCosto: number = 0.0;
    precioVenta: number = 0.0;
    iva: any;
    idIva: any;
    contenidoNeto: string = "";
    detalle: string = "";
    pesable: boolean = false;
    ingresoCantidadManual: boolean = false;
    codigoBarras: Array<any> = [];
    proveedores: Array<any> = [];
    imagenProducto: Array<ImagenProducto> = [];
    imagen: ImagenProducto;
    precios: Array<any> = [];
    stockSucursales: Array<any> = [];
    unidad: any;
    tipo: number;
    productosCompuestos: Array<any> = [];
    $$activo:any;
    activo:any;
}
