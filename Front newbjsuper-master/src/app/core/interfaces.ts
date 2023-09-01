import { QueryParamsHandling } from "@angular/router";

export interface IItemCbte {
    baseImponibleTotal: number;
    cantidad: number;
    descuento: number;
    adicional: number;
    id: number;
    ivaId: any
    ivaDes: any
    ivaTotal: number;
    ivaUnidad: 12.6
    porcentajeDescuento: number;
    precioConIva: number;
    precioSinIva: number;
    precioCosto: number;
    precio: number;
    producto: any;
    idProducto: any;
    nombreProducto: any;
    total: number;
    subtotal: number;
    infoProdAdic: any;
}
export interface ITotalCbte {
    totalAdicionales: number;
    totalDescuentos: number;
    totalImpuestos: number;
    totalTributos: number;
    totalIVAs: number;
    totalBaseImp: number;
    subtotal: number;
    total: number;
}
export interface ICbteCompra {
    concepto: string;
    proveedor: any;
    nombreProveedor: string;
    direccionProveedor: string;
    tipoDocProveedor: string;
    tipoProveedor: string;
    nroDocProveedor: string;
    sucursal: any;
    tipoCbte: number;
    tipoFactura: number;
    numero: string;
    fechaCbte: string;
    fechaVencCbte: string;
    fechaServDesde: string;
    fechaServHasta: string;
    cae: string;
    caeVenc: string;
    observacion: string;
    totalAdicionales: number;
    totalDescuentos: number;
    totalImpuestos: number;
    totalTributos: number;
    totalIVAs: number;
    totalBaseImp: number;
    subtotal: number;
    total: number;
    items: Array<IItemCbte>;
    pagos: Array<IPago>;
    tributos: Array<ITributos>;
}
export interface ICbte {
    origen: string;
    concepto: string;
    cliente: any;
    nombreCliente: string;
    direccionCliente: string;
    tipoDocCliente: string;
    tipoCliente: string;
    nroDocCliente: string;
    sucursal: any;
    tipoCbte: number;
    numero: string;
    tipoFactura: boolean;
    fechaCbte: string;
    fechaVencCbte: string;
    fechaServDesde: string;
    fechaServHasta: string;
    cae: string;
    observacion: string;
    razonSocialEmpresa: string;
    condicionEmpresa: string;
    fechaIniActEmpresa: string;
    ingBrutosEmpresa: string;
    cuitEmpresa: string;
    domComercialEmpresa: string;
    totalAdicionales: number;
    totalDescuentos: number;
    totalImpuestos: number;
    totalTrib: number;
    totalIva: number;
    totalBaseImp: number;
    subtotal: number;
    ptoVenta: number;
    total: number;
    detalleVentas: Array<IItemCbte>;
    pagosCbte: Array<IPago>;
    tributos: Array<ITributos>;
    listaPrecio: boolean;
    listaPrecioData: string;
}
export interface IPago {
    asociadaTipo: number;
    asociadaId: number;
    descripcion: string;
    monto: number;
    numero: string;
    tarjeta: string;
    tipo: any;
    formaPago: any;
}
export interface IHeaderCbte {
    cliente: any,
    nombreCliente: string,
    direccionCliente: string,
    tipoDocCliente: any,
    tipoCliente: any,
    nroDocCliente: any,
    tipoCbte: any,
    fechaCbte: any,
    fechaVencCbte: any,
    fechaVigencia: any,
    concepto: any,
    
}
export interface IImpuesto {
    id: number;
    nombre: string;
    importe: number;
    porcentaje: number;
    impuesto: any
}
export interface ITributos {
    id: number;
    idAfipTpoTributo: number;
    descAfipTpoTributo: string;
    descripcion: string;
    alicuota: number;
    importe: number;
    baseImponible: number;
}
export interface MegaMenuItem {
    label?: string;
    icon?: string;
    command?: (event?: any) => void;
    url?: string;
    items?: MenuItem[][];
    expanded?: boolean;
    disabled?: boolean;
    visible?: boolean;
    target?: string;
    routerLinkActiveOptions?: any;
    separator?: boolean;
    badge?: string;
    badgeStyleClass?: string;
    style?:any;
    styleClass?:string;
    iconStyle?: any;
    title?: string;
    id?: string;
    automationId?: any;
    tabindex?: string;
    routerLink?: any;
    queryParams?: { [k: string]: any };
    fragment?: string;
    queryParamsHandling?: QueryParamsHandling;
    preserveFragment?: boolean;
    skipLocationChange?: boolean;
    replaceUrl?: boolean;
    state?: {
        [k: string]: any;
    }
}
export interface MenuItem {
    label?: string;
    icon?: string;
    command?: (event?: any) => void;
    url?: string;
    items?: MenuItem[];
    expanded?: boolean;
    disabled?: boolean;
    visible?: boolean;
    target?: string;
    escape?: boolean;
    routerLinkActiveOptions?: any;
    separator?: boolean;
    badge?: string;
    tooltip?: string;
    tooltipPosition?: string;
    badgeStyleClass?: string;
    style?:any;
    styleClass?:string;
    title?: string;
    id?: string;
    automationId?: any;
    tabindex?: string;
    routerLink?: any;
    queryParams?: { [k: string]: any };
    fragment?: string;
    queryParamsHandling?: QueryParamsHandling;
    preserveFragment?: boolean;
    skipLocationChange?: boolean;
    replaceUrl?: boolean;
    iconStyle?: any;
    iconClass?: string;
    state?: {
        [k: string]: any;
    }
    tooltipOptions?: {
        tooltipLabel?: string;
        tooltipPosition?: string;
        tooltipEvent?: string;
        appendTo?: any;
        positionStyle?: string;
        tooltipStyleClass?: string;
        tooltipZIndex?: string;
        escape?: boolean;
        disabled?: boolean;
        positionTop?: number;
        positionLeft?: number;
        showDelay?: number;
        hideDelay?: number;
        life?: number;
    }
}
