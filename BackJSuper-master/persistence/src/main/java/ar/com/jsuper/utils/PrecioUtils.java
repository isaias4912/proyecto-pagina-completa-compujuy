/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author rafa
 */
public class PrecioUtils {

	public static BigDecimal addPorcentaje(BigDecimal precioAdd, BigDecimal valor, Integer tipo) {
		BigDecimal precio = BigDecimal.ZERO;
		if (tipo.equals(1)) {//porcentaje
			precio = precioAdd.add(
					(valor.multiply(precioAdd, MathContext.DECIMAL128)).divide(new BigDecimal("100"), MathContext.DECIMAL128)
			);
		}
		if (tipo.equals(2)) {//porcentaje
			precio = precioAdd.add(valor, MathContext.DECIMAL128);
		}
		precio = precio.setScale(2, RoundingMode.DOWN);
		return precio;
	}
}
