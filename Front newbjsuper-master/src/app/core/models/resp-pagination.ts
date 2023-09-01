
export class RespPagination<T> {
    data:  Array<T> = [];
    dataSet: any = "";
    page: number;
    pageSize: number;
    total: number;
}
