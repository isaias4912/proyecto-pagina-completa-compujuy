package ar.com.jsuper.api.reportes;

import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.services.ClientesService;
import ar.com.jsuper.services.reportes.ReporteClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/reportes/clientes")
public class ClientesReportesController {

    @Autowired
    ReporteClientes reporteClientes;
    @Autowired
    ClientesService clientesService;

    @RequestMapping(value = "/{typePrint}/ctacte/mto/all", params = {"order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] printMovimientosCtaCte(@PathVariable String typePrint, @RequestBody FilterMovCtaCte filterMovCtaCteDTO, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
        PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> data = clientesService.getMovimientosCtaCte(filterMovCtaCteDTO, 0, 0, orden, reverse);
        return this.reporteClientes.printMovimientosCtaCte(data);
    }
}
