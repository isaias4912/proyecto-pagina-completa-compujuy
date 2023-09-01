package ar.com.jsuper.api.reportes;

import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import ar.com.jsuper.services.reportes.ReportGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.CbteVenEncMinDTO;
import ar.com.jsuper.dto.FilterVentasDTO;
import ar.com.jsuper.dto.GenericDataDTO;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.reportes.ReporteVentas;

import java.util.Base64;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/reportes/ventas")
public class VentasReportesController {

    @Autowired
    ReporteVentas reporteVentas;
    @Autowired
    private VentasService ventasService;

    @Autowired
    ReportGeneric reportGeneric;

    @RequestMapping(value = "/{typePrint}/list", params = {"order", "reverse"}, method = RequestMethod.POST, produces = "application/pdf")
    public byte[] printList(@PathVariable String typePrint, @RequestBody FilterVentasDTO filterVentasDTO, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
        List<CbteVenEncMinDTO> ventasListDTO = ventasService.getVentas(filterVentasDTO, orden, reverse);
        return this.reportGeneric.printTable(
                new DataReportGeneric(ventasListDTO, ColumnGeneric.getColumns(
                        new String[]{"Id", "Fecha de carga", "Tipo Cbte.", "Cliente", "Usuario", "Pagada", "Saldo", "Total"},
                        new String[]{"id", "fechaCarga", "tipoCbte", "nombreCliente", "usuario.usuario", "pagada", "saldo", "total"},
                        new int[]{10, 16, 5, 22, 15, 10, 10, 10}
                ), "Lista de ventas"
                ), typePrint);
    }

    @RequestMapping(value = "/cbte/pdf/{id}", params = {"tipo"}, method = RequestMethod.GET, produces = "application/pdf")
    public byte[] printFEPDF(@PathVariable Integer id, @RequestParam(value = "tipo", required = false) String tipo) {
        CbteVenEncDTO cbteVenEncDTO = ventasService.get(id, true);
        return reporteVentas.printTicketFactura(tipo, cbteVenEncDTO);
    }

    /**
     * Responde un data en Base 64
     *
     * @param id
     * @param tipo
     * @return GenericDataDTO
     * @throws BussinessException
     */
    @RequestMapping(value = "/cbte/pdf/base64/{id}", params = {"tipo"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericDataDTO printFEPDFBase64(@PathVariable Integer id, @RequestParam(value = "tipo", required = false) String tipo) {
        CbteVenEncDTO cbteVenEncDTO = ventasService.get(id, true);
        byte[] responseByte = reporteVentas.printTicketFactura(tipo, cbteVenEncDTO);
        return new GenericDataDTO<>(Base64.getEncoder().encodeToString(responseByte));
    }

}
