export enum Roles {
    ROLE_ADMIN = 'ROLE_ADMIN',
    ROLE_GERENTE = 'ROLE_GERENTE',
    ROLE_CAJERO = 'ROLE_CAJERO',
    ROLE_VENDEDOR = 'ROLE_VENDEDOR'
}
export enum Comprobante {
    FACTURA_A = 'A',
    FACTURA_B = 'B',
    FACTURA_C = 'C',
    CONS_FINAL = 'CF',
    FACTURA_x = 'X',
    PRESUPUESTO = 'PR'
}

export enum TipoClienteDesc {
    MONOTRIBUTO = 'Monotr.',
    RESPONSABLE_INSCRIPTO = 'Resp. Insc.',
    EXCENTO = 'Exc.',
    CONSUMIDOR_FINAL = 'Cons. Final',
    SIN_ESPECIFICAR = 'X',
    OTRO = 'Otro'
}
export enum TipoCliente {
    MONOTRIBUTO = 'Monotr.',
    RESPONSABLE_INSCRIPTO = 'Resp. Insc.',
    EXCENTO = 'Exc.',
    CONSUMIDOR_FINAL = 'Cons. Final',
    SIN_ESPECIFICAR = 'X',
    OTRO = 'Otro'
}
export enum TipoCbte {
    CBTE_VENTA = 1,
    CBTE_COMPRA = 2,
    CBTE_PRESUPUESTO = 3
}

export enum TipoSearch {
    SEARCH_FILTER = 1,
    SEARCH_WITHOUT_FILTER = 2,
}