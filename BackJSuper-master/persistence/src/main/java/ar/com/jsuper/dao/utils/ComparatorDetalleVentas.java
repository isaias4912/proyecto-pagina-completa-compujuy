/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dao.utils;

import ar.com.jsuper.dto.CbteVenDetSinEncabDTO;
import java.util.Comparator;

/**
 *
 * @author rafa
 */
public class ComparatorDetalleVentas implements Comparator<CbteVenDetSinEncabDTO> {

	@Override
	public int compare(CbteVenDetSinEncabDTO o1, CbteVenDetSinEncabDTO o2) {
		if (!o1.getId().equals(o2.getId())) {
			return o1.getId().compareTo(o2.getId());
		} else {
			return -1;
		}
	}
}
