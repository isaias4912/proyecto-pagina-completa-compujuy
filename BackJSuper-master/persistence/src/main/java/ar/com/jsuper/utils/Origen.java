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
public enum Origen {
	PUNTO_VENTA_DESKTOP(1),
	PUNTO_VENTA_WEB(2),
	PUNTO_VENTA_MOBILE(3);

	public Integer theState;

	private Origen(Integer aState) {
		this.theState = aState;
	}

	public static Origen value(Integer val) {
		Origen[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Origen value = var1[var3];
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
