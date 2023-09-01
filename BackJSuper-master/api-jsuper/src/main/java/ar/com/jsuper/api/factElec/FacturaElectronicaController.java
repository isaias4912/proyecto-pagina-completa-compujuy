/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api.factElec;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.AfipEncFacturaDTO;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/fact/fe")
public class FacturaElectronicaController {

	@Autowired
	FacturaElectronicaService facturaElectronicaService;

//	@Autowired
//	public FacturaElectronicaService facturaElectronicaService(URL urlWSDLAfip) {
//		return 
//	}

	/**
	 * devuelve los datos de la app
	 *
	 */
	@RequestMapping(value = "/tipocomprobantes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getTipoComprobantes() throws BussinessException {
		return new ResponseEntity<>(Response.ok(facturaElectronicaService.getTipoComprobantes()), HttpStatus.OK);
	}

	/**
	 * devuelve token de afip
	 *
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getToken() throws BussinessException {
		return new ResponseEntity<>(Response.ok(facturaElectronicaService.getToken()), HttpStatus.OK);
	}

	/**
	 * Devuelve los datos de la base de datos, segun lo que esta cargado en afip
	 *
	 * @return
	 */
	@RequestMapping(value = "/datafe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getDataFE() {
		return new ResponseEntity<>(Response.ok(facturaElectronicaService.getDataFE()), HttpStatus.OK);
	}

	/**
	 * Devuelve los datos de la base de datos, segun lo que esta cargado en afip
	 *
	 * @return
	 */
	@RequestMapping(value = "/datafe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveDataFE() throws BussinessException {
		facturaElectronicaService.actualizarDataFE();
		return new ResponseEntity<>(Response.ok(facturaElectronicaService.getDataFE()), HttpStatus.OK);
	}

//	/**
//	 * Devuelve los datos de la base de datos, segun lo que esta cargado en afip
//	 *
//	 * @return
//	 */
//	@RequestMapping(value = "/solicitarCAE", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity factura(@RequestBody AfipEncFacturaDTO afipEncFacturaDTO) throws BussinessException {
//		return new ResponseEntity<>(Response.ok(facturaElectronicaService.solicitarCAE(afipEncFacturaDTO)), HttpStatus.OK);
//	}

	/**
	 * Devuelve una previa de lo que seria una vista
	 *
	 * @return
	 */
	@RequestMapping(value = "/previewCAE", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity previewFactura(@RequestBody AfipEncFacturaDTO afipEncFacturaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(facturaElectronicaService.previewFE(afipEncFacturaDTO)), HttpStatus.OK);
	}

}
