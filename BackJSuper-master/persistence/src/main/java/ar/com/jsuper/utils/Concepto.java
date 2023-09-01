/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

/**
 *
 * @author rafa
 */
public enum Concepto {
	PRODUCTOS(1),
	SERVICIOS(2),
	PRODUCTOS_SERVICIOS(3);

	public Integer theState;

	private Concepto(Integer aState) {
		this.theState = aState;
	}

	public static Concepto value(Integer val) {
		Concepto[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Concepto value = var1[var3];
			if (value.toInt().equals(val)) {
				return value;
			}
		}

		return null;
	}

	public Integer toInt() {
		return this.theState;
	}
}
