/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.FilterProductoDTO;
import ar.com.jsuper.dto.reportes.BarCodesReqDTO;

import java.util.List;

/**
 * @author rafael
 */
public interface ReporteProductos extends Reporte {

    byte[] printListProductosPDF(FilterProductoDTO productoFilter, String fieldOrder, boolean reverse, String type);

    byte[] printBarCodesColsPDF(List<BarCodesReqDTO> barCodesReqDTOs, Integer cols, Integer style, String barcode);

    byte[] printBarCodesCols(List<BarCodesReqDTO> barCodesReqDTOs, Integer cols, Integer style, String barcode, String typePrint);

    byte[] printListProductos(FilterProductoDTO productoFilter, String fieldOrder, boolean reverse, String type, String typePrint);

    byte[] printDetailProductoPDF(Integer idProducto);
}
