
export class ResponsePagination<T> {
    constructor(data?: T) {
        this.data = data;
    }
    data: T;
    dataSet:T;
    total: number;
    pageSize: number;
    page: number;
    extraData: any;
}
