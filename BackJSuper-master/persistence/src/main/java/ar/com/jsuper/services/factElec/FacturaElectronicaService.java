/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.factElec;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.dto.AfipEncFacturaDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.services.factElec.ws.CbteTipo;
import ar.com.jsuper.services.factElec.ws.FEAuthRequest;
import ar.com.jsuper.utils.ResponseSolCae;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rafa
 */
public interface FacturaElectronicaService {

	public List<CbteTipo> getTipoComprobantes() throws BussinessException;

	public FEAuthRequest getToken() throws BussinessException;

	public Map getDataFE();

	public Boolean actualizarDataFE() throws BussinessException;

//	public Map solicitarCAE(AfipEncFacturaDTO factura) throws BussinessException;

	public ResponseSolCae solicitarCAEFromEncVentas(CbteEncVenta encVenta, Configuracion configuracions) throws BussinessException;

	public CbteVenEncDTO previewFE(AfipEncFacturaDTO factura);

	public void enableProduccionAfip(Integer value);

	public Map getDataVentasWeb();

	public Map getPuntoVentas();
}
