/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.services.factElec.ws.FECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.FECAEResponse;

/**
 *
 * @author rafa
 */
public class ResponseSolCae {

	private FECAEResponse response;
	private Boolean resultado;
	private FECAEDetRequest request;

	public FECAEResponse getResponse() {
		return response;
	}

	public void setResponse(FECAEResponse response) {
		this.response = response;
	}

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

	public FECAEDetRequest getRequest() {
		return request;
	}

	public void setRequest(FECAEDetRequest request) {
		this.request = request;
	}

}
