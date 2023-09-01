import { IDataForm } from "../../../core/models/idata-form";

export interface IDataFormProducto {
  informacionBasica: IDataForm;
  imagenes: IDataForm;
  precios: IDataForm;
  existencia: IDataFormExistencia;
  productoCompuesto: IDataForm;
}
export interface IDataFormExistencia {
  codigosBarra: IDataForm;
  stockSucursal: IDataForm;
  proveedores: IDataForm;
}
