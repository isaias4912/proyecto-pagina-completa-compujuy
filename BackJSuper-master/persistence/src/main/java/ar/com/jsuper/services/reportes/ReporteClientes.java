/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes;

import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.dto.CbtePresupuestoEncDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.InfoCtaCteDTO;
import ar.com.jsuper.dto.MovimientosCtaCteDTO;

/**
 * @author rafael
 */
public interface ReporteClientes extends Reporte {

    byte[] printMovimientosCtaCte(PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> data);

}
