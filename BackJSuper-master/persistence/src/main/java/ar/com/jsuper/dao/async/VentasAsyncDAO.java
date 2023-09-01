/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dao.async;

import ar.com.jsuper.domain.CbteEnc;
import ar.com.jsuper.domain.StockSucursal;

/**
 * @author rafa
 */
public interface VentasAsyncDAO {
    void saveMovimientoProducto(CbteEnc cbteEnc, StockSucursal ss);
}
