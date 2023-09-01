/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rafa22
 */
public class Constants {

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static Integer ID_EFECTIVO = 1;
	public static Integer ID_TARJETA_CRED = 2;
	public static Integer ID_TARJETA_DEB = 3;
	public static Integer ID_CUENTA_CORRIENTE = 4;
	public static final Map<Integer, String> CONDICION_AFIP = createMapCondAfip();


	private static Map<Integer, String> createMapCondAfip() {
		Map<Integer, String> myMap = new HashMap<>();
		myMap.put(1, "Monotributo");
		myMap.put(2, "Responsable Inscripto");
		myMap.put(3, "Exento");
		return myMap;
	}

	public final static ArrayNode getTpoCbtesCompra() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode temp = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		temp.put("id", 1);
		temp.put("descripcion", "Factura A");
		temp.put("nombre", Comprobante.A.toString());
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 6);
		temp.put("descripcion", "Factura B");
		temp.put("nombre", Comprobante.B.toString());
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 11);
		temp.put("descripcion", "Factura C");
		temp.put("nombre", Comprobante.C.toString());
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 500);// revisar
		temp.put("descripcion", "CONS. FINAL");
		temp.put("nombre", Comprobante.CF.toString());
		arrayNode.add(temp);
		temp = mapper.createObjectNode();
		temp.put("id", 501);
		temp.put("descripcion", "FACTURA X");
		temp.put("nombre", Comprobante.X.toString());
		arrayNode.add(temp);
		return arrayNode;
	}

	

//    private static String getCondicionFromId() {
//        CONDICION_AFIP.
}
