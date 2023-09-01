package ar.com.jsuper.api.reportes;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.ProductoListDTO;
import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import ar.com.jsuper.services.ProductosService;
import ar.com.jsuper.services.reportes.ReportGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.dto.FilterProductoDTO;
import ar.com.jsuper.dto.reportes.BarCodesReqDTO;
import ar.com.jsuper.services.SecurityService;
import ar.com.jsuper.services.reportes.ReporteProductos;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/reportes/productos")
public class ProductosReportesController {

    @Autowired
    ReporteProductos reporteProductos;
    @Autowired
    SecurityService<Producto> securityService;

    @Autowired
    ProductosService productosService;
    @Autowired
    ReportGeneric reportGeneric;

    @RequestMapping(value = "/{typePrint}/list", params = {"order", "reverse", "type"}, method = RequestMethod.POST, produces = "application/pdf")
    public byte[] printList(@PathVariable String typePrint, @RequestBody FilterProductoDTO productoFilter, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "type", required = false) String type) throws BussinessException {
        List<ProductoListDTO> productoListDTO = productosService.getDetalleProducto(productoFilter, orden, reverse, type);
        return this.reportGeneric.printTable(
                new DataReportGeneric(productoListDTO, ColumnGeneric.getColumns(
                        new String[]{"Id", "Cod. Interno", "Prod. padre", "Nombre", "Familia", "Cont. neto", "Precio Venta"},
                        new String[]{"id", "codigoEspecial", "productoPadre.nombre", "nombreFinal", "productoPadre.familia.nombre", "contenidoNeto", "precioVenta"},
                        new int[]{10, 16, 20, 20, 15, 10, 10}
                ), "Lista de productos"
                ), typePrint);
    }

    @RequestMapping(value = "/pdf/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public byte[] printDetailPDF(@PathVariable Integer id) {
        return reporteProductos.printDetailProductoPDF(id);
    }

    @RequestMapping(value = "/barcodes/{typePrint}", params = {"cols", "style"}, method = RequestMethod.POST, produces = "application/pdf")
    public byte[] printBarCodePDF(@PathVariable String typePrint, @RequestBody List<BarCodesReqDTO> barCodesReqDTOs, @RequestParam(value = "cols", required = true) Integer cols, @RequestParam(value = "style", required = true) Integer style,
                                  @RequestParam(value = "barcode", required = true) String barcode) {
        if (typePrint.equals("pdf")) {
            return reporteProductos.printBarCodesColsPDF(barCodesReqDTOs, cols, style, barcode);
        } else {
            return reporteProductos.printBarCodesCols(barCodesReqDTOs, cols, style, barcode, typePrint);
        }
    }
}
