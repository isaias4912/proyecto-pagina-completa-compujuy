package ar.com.jsuper.api.reportes;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.utils.FilterCbte;
import ar.com.jsuper.domain.utils.PaginationRequestDTO;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.dto.reportes.ColumnGeneric;
import ar.com.jsuper.dto.reportes.DataReportGeneric;
import ar.com.jsuper.services.PresupuestoService;
import ar.com.jsuper.services.reportes.ReportGeneric;
import ar.com.jsuper.services.reportes.ReporteVentas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes/presupuesto")
public class PresupuestoReportesController {

    @Autowired
    ReportGeneric reportGeneric;
    @Autowired
    ReporteVentas reporteVentas;
    @Autowired
    private PresupuestoService presupuestoService;

    @PostMapping(value = "/{typePrint}/list")
    public byte[] printList(@PathVariable String typePrint,
                            @RequestBody FilterCbte filterCbte,
                            @Valid PaginationRequestDTO paginationRequestDTO
    ) {
        List<CbtePresEncMinDTO> presupuestos = presupuestoService.getPresupuestos(filterCbte, paginationRequestDTO);

        return this.reportGeneric.printTablePdf(
                new DataReportGeneric(presupuestos, ColumnGeneric.getColumns(
                        new String[]{"Id", "Fecha de carga","Usuario", "Nombre del cliente", "Estado", "monto"},
                        new String[]{"id", "fechaCarga","usuario.usuario", "nombreCliente", "estadoCbte", "total"},
                        new int[]{10, 16, 11, 35, 18, 10}
                ), "Lista de presupuestos"
                ));
    }

    @RequestMapping(value = "/cbte/pdf/{id}", params = {"tipo"}, method = RequestMethod.GET, produces = "application/pdf")
    public byte[] printFEPDF(@PathVariable Integer id, @RequestParam(value = "tipo", required = false) String tipo) {
        CbtePresupuestoEncDTO cbtePresupuestoEncDTO = presupuestoService.get(id);
        return reporteVentas.printCbtePresupueso(tipo, cbtePresupuestoEncDTO);
    }

}
