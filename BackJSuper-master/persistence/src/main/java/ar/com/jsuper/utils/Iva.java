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
public enum Iva {
	a(1),
	aa(6),
	C(11),
	CF(500),
	X(501);

	public Integer theState;

	private Iva(Integer aState) {
		this.theState = aState;
	}

	public static Iva value(Integer val) {
		Iva[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Iva value = var1[var3];
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
