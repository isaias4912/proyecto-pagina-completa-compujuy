/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.domain.utils.DetalleVentasTicket;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.CbteVenDetSinEncabDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rafael
 */
public class UtilsVenta {

	public static List<DetalleVentasTicket> getDetallesTicket(List<CbteVenDetSinEncabDTO> detalleVentas, ConfiguracionDTO configuracion) {
		List<DetalleVentasTicket> detalleVentasTickets = new ArrayList<>();
		for (CbteVenDetSinEncabDTO detalleVenta : detalleVentas) {
			DetalleVentasTicket detalleVentasTicket = new DetalleVentasTicket();
			String descripcion = "";
			if (configuracion.getCutDescTicket() != null && configuracion.getCutDescTicket()) {
				if (configuracion.getSizeDescTicket() != null && configuracion.getSizeDescTicket() > 2) {
					descripcion = cutDescripcion(detalleVenta.getDescripcion().trim(), configuracion.getSizeDescTicket());
				} else {
					descripcion = detalleVenta.getDescripcion().trim();
				}
			} else {
				descripcion = detalleVenta.getDescripcion().trim();
			}
			if (!Objects.isNull(detalleVenta.getNumeroSerie()) && !detalleVenta.getNumeroSerie().equals("")) {
				descripcion = descripcion + " - " + detalleVenta.getNumeroSerie();
			}
			if (!Objects.isNull(detalleVenta.getInfoProdAdic()) && !detalleVenta.getInfoProdAdic().equals("")) {
				descripcion = descripcion + " - " + detalleVenta.getInfoProdAdic();
			}
			detalleVentasTicket.setDescripcion(descripcion + " - " + NumbersUtils.roundCutTwoDec(detalleVenta.getCantidad()) + " x " + NumbersUtils.roundCutTwoDec(detalleVenta.getPrecio()));
			if (!Objects.isNull(detalleVenta.getOferta())) {
				if (detalleVenta.getOferta()) {
					if (!Objects.isNull(detalleVenta.getOfertaDescuento())) {
						if (detalleVenta.getOfertaDescuento().compareTo(BigDecimal.ZERO) > 0) {
							detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(-" + NumbersUtils.roundCutTwoDec(detalleVenta.getOfertaDescuento()) + ")");
						}
					}
				}
			}
			if (!Objects.isNull(detalleVenta.getDescuento())) {
				if (detalleVenta.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
					detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(-" + NumbersUtils.roundCutTwoDec(detalleVenta.getDescuento()) + ")");
				}
			}
			if (!Objects.isNull(detalleVenta.getAdicional())) {
				if (detalleVenta.getAdicional().compareTo(BigDecimal.ZERO) > 0) {
					detalleVentasTicket.setDescripcion(detalleVentasTicket.getDescripcion().trim() + "(+" + NumbersUtils.roundCutTwoDec(detalleVenta.getAdicional()) + ")");
				}
			}
			detalleVentasTicket.setSubtotal(detalleVenta.getTotal());
			detalleVentasTickets.add(detalleVentasTicket);
		}
		return detalleVentasTickets;
	}

	public static String cutDescripcion(String descripcion, Integer lenghtCut) {
		String[] splited = descripcion.split("\\s+");
		StringBuilder st = new StringBuilder("");
		for (int i = 0; i < splited.length; i++) {
			if (splited[i].length() > lenghtCut) {
				splited[i] = splited[i].substring(0, lenghtCut);
				try {
					Double.parseDouble(splited[i].trim());
				} catch (NumberFormatException e) {
					splited[i] = splited[i].substring(0, lenghtCut);
				}
			}
			st.append(splited[i]).append(" ");
		}
		return st.toString().trim();
	}
}
