import { DataCount } from "../interfaces/idataCount";

export class ParamPagination implements DataCount{
    public _format: string = "json";
    public page: number = 1;
    public pageSize: number = 10;
    public order: string = "id";
    public reverse: boolean = false;
    public type: string = "AND";
    public q: string = "";
    public sortkey: string = "";
    public direction: string = "ASC";
    public itemsPerPage: number = 10;
    public totalItems: number = 0;
    public maxSize: number = 5;
    public bigTotalItems: number = 175;
    public totalPages: number = 0;
    public bigCurrentPage: number = 1;
    public filtroNombre: string = "";
    getSize(): number {
        console.log('this.totalItems', this.totalItems)
        return this.totalItems;
    }
}
