/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.services.factElec.ws.ArrayOfFECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.ArrayOfFECAEDetResponse;
import ar.com.jsuper.services.factElec.ws.FECAECabResponse;
import ar.com.jsuper.services.factElec.ws.FECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.FECAEDetResponse;
import ar.com.jsuper.services.factElec.ws.FECAERequest;
import ar.com.jsuper.services.factElec.ws.FECAEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author rafa
 */
public class ConstanstAfipTest {

	public final static ArrayNode getTpoCbtes() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 1);
		temp.put("descripcion", "Factura A");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 2);
		temp.put("descripcion", "Nota de Débito A");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 3);
		temp.put("descripcion", "Nota de Crédito A");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 6);
		temp.put("descripcion", "Factura B");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 7);
		temp.put("descripcion", "Nota de Débito B");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 8);
		temp.put("descripcion", "Nota de Crédito B");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 11);
		temp.put("descripcion", "Factura C");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 12);
		temp.put("descripcion", "Nota de Débito C");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 13);
		temp.put("descripcion", "Nota de Crédito C");
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getTpoConceptos() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 1);
		temp.put("nombre", Concepto.PRODUCTOS.name());
		temp.put("descripcion", "Producto");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 2);
		temp.put("nombre", Concepto.SERVICIOS.name());
		temp.put("descripcion", "Servicios");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 3);
		temp.put("nombre", Concepto.PRODUCTOS_SERVICIOS.name());
		temp.put("descripcion", "Productos y Servicios");
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getTpoFacturas(Integer tipo) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 1);
		temp.put("nombre", Factura.FACTURA_ELECTRONICA.name());
		temp.put("descripcion", "Factura electrónica");
		temp.put("abrev", "F.E.");
		arrayNode.add(temp);
		if (tipo.equals(2)) {// por ahora solo para compras
			temp = mapper.createObjectNode();
			temp.put("id", 2);
			temp.put("nombre", Factura.FACTURA_MANUAL.name());
			temp.put("descripcion", "Factura manual");
			temp.put("abrev", "F.M.");
			arrayNode.add(temp);
		}
		if (tipo.equals(2)) {// por ahora solo para compras
			temp = mapper.createObjectNode();
			temp.put("id", 3);
			temp.put("nombre", Factura.TICKET_FACTURA_FISCAL.name());
			temp.put("descripcion", "Ticket/Factura fiscal");
			temp.put("abrev", "F/T. Fisc.");
			arrayNode.add(temp);
		}
		temp = mapper.createObjectNode();
		temp.put("id", 20);
		temp.put("nombre", Factura.TIKE_FACTURA_NO_VALIDA.name());
		temp.put("descripcion", "Ticket/Factura no válida");
		temp.put("abrev", "F/T no val.");
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getPtoVentas() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("nro", 1);
		temp.put("descripcion", "Punto de venta uno");
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getTpoIvas() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 3);
		temp.put("descripcion", "0%");
		temp.put("value", 0);
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 4);
		temp.put("descripcion", "10.5%");
		temp.put("value", 10.5);
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 5);
		temp.put("descripcion", "21%");
		temp.put("value", 21);
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 6);
		temp.put("descripcion", "27%");
		temp.put("value", 27);
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 8);
		temp.put("descripcion", "5%");
		temp.put("value", 5);
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 9);
		temp.put("descripcion", "2.5%");
		temp.put("value", 2.5);
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getTpoDocs() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 80);
		temp.put("nombre", Doc.CUIT.name());
		temp.put("descripcion", "CUIT");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 86);
		temp.put("nombre", Doc.CUIL.name());
		temp.put("descripcion", "CUIL");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 87);
		temp.put("nombre", Doc.CDI.name());
		temp.put("descripcion", "CDI");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 89);
		temp.put("nombre", Doc.LE.name());
		temp.put("descripcion", "LE");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 90);
		temp.put("nombre", Doc.LC.name());
		temp.put("descripcion", "LC");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 96);
		temp.put("nombre", Doc.DNI.name());
		temp.put("descripcion", "DNI");
		arrayNode.add(temp);
		return arrayNode;
	}

	public final static ArrayNode getTpoTributos() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 1);
		temp.put("descripcion", "Impuestos nacionales");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 2);
		temp.put("descripcion", "Impuestos provinciales");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 3);
		temp.put("descripcion", "Impuestos municipales");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 4);
		temp.put("descripcion", "Impuestos Internos");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 5);
		temp.put("descripcion", "IIBB");
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 99);
		temp.put("descripcion", "Otro");
		arrayNode.add(temp);
		return arrayNode;
	}

	public static FECAEResponse fecaeSolicitarTest(FECAERequest request, Long cuit) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = new Date();
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		// manipulate date
		c.add(Calendar.DAY_OF_YEAR, 10);

		// convert calendar to date
		Date fechaVencCAE = c.getTime();

		String fechaProceso = dateFormat.format(new Date());
		fechaProceso = fechaProceso + "000000";
		FECAEResponse response = new FECAEResponse();
		FECAEDetResponse feCAEDetResponse = new FECAEDetResponse();
		ArrayOfFECAEDetResponse aofecaedr = new ArrayOfFECAEDetResponse();

		FECAECabResponse cab = new FECAECabResponse();
		cab.setCantReg(1);
		cab.setPtoVta(request.getFeCabReq().getPtoVta());
		cab.setCbteTipo(request.getFeCabReq().getCbteTipo());
		cab.setCuit(cuit);
		cab.setFchProceso(fechaProceso);
		cab.setResultado("A");
		cab.setReproceso("N");

		ArrayOfFECAEDetRequest aEDetRequest = request.getFeDetReq();
		List<FECAEDetRequest> listaFECAEDetRequest = aEDetRequest.getFECAEDetRequest();
		for (FECAEDetRequest fECAEDetRequest : listaFECAEDetRequest) {
			String generatedStringCAE = RandomStringUtils.random(14, false, true);
			feCAEDetResponse.setCAE(generatedStringCAE);
			feCAEDetResponse.setCAEFchVto(dateFormat.format(fechaVencCAE));
			feCAEDetResponse.setCbteDesde(0);
			feCAEDetResponse.setCbteHasta(0);
			feCAEDetResponse.setCbteFch(fECAEDetRequest.getCbteFch());
			feCAEDetResponse.setConcepto(fECAEDetRequest.getConcepto());
			feCAEDetResponse.setDocNro(fECAEDetRequest.getDocNro());
			feCAEDetResponse.setDocTipo(fECAEDetRequest.getDocTipo());
			feCAEDetResponse.setObservaciones(null);
			feCAEDetResponse.setResultado("A");
			aofecaedr.getFECAEDetResponse().add(feCAEDetResponse);
		}

		response.setErrors(null);
		response.setFeCabResp(cab);
		response.setFeDetResp(aofecaedr);
		return response;
	}
}
