/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.factElec;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.services.factElec.ws.FEAuthRequest;

/**
 *
 * @author rafa
 */
public interface ConeccionFEService {

	public FEAuthRequest getToken() throws BussinessException;

	public FEAuthRequest getToken(Configuracion configuracion) throws BussinessException;
}
