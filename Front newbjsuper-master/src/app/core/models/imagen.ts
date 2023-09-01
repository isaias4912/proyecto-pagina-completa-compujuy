
export class Imagen {
    public constructor(init?: Partial<Imagen>) {
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
