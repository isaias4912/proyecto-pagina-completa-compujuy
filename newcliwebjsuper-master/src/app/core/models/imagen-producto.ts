
export class ImagenProducto {
    public constructor(init?: Partial<ImagenProducto>) {
        Object.assign(this, init);
    }
    id: number;
    nombre: string = "";
	path: string = "";
	tag: string = "";
	tamanio: string = "";
	tipo: string = "";
	alt: string = "";
	orden: number;
}
