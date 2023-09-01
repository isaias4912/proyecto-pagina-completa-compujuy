
export class Response<T> {
    constructor(data?: T) {
        this.data = data;
    }
    code: number;
    message: string;
    data: T;
}
