/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes;

//import java.awt.image.BufferedImage;

import ar.com.jsuper.dto.CbtePresupuestoEncDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;

import java.util.Map;

/**
 * @author rafael
 */
public interface Reporte {

    Map<String, Object> getEncabezadoReporte();

    Map<String, Object> getData(CbteVenEncDTO cbteVenEnc);

    Map<String, Object> getData(CbtePresupuestoEncDTO cbtePresupuestoEncDTO);

    Map<String, Object> getData(CbteVenEncDTO cbteVenEnc, ConfiguracionDTO configuracionDTO);

    Map<String, Object> getBaseData();

}
