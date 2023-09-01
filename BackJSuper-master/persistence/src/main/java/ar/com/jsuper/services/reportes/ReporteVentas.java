/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes;

import ar.com.jsuper.dto.CbtePresupuestoEncDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;

/**
 * @author rafael
 */
public interface ReporteVentas extends Reporte {

    byte[] printTicketFactura(String tipo, CbteVenEncDTO cbteVenEncDTO);

    byte[] printCbtePresupueso(String tipo, CbtePresupuestoEncDTO cbtePresupuestoEncDTO);

    byte[] printCbteVenPDF(CbteVenEncDTO cbteVenEncDTO);

    byte[] printCbtePresupuestoPDF(CbtePresupuestoEncDTO cbtePresupuestoEncDTO);

    byte[] printTicketFacturaPDF(String tipo, CbteVenEncDTO cbteVenEncDTO);

}
