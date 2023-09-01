export declare interface ISelect {
    search(value: string): void;
    setData(data: any, event: boolean);
    setLabel(data: any);
    isNotEqualValue(data: any);
    eventNextFocus();
    eventInitFocus();
    resetData(event: boolean, focus: boolean);
    isValid(value: string): boolean;
    showDetail();
}

