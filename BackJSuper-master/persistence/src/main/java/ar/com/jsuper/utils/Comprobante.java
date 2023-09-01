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
public enum Comprobante {
	A(1),
	B(6),
	C(11),
	CF(500),
	X(501), // pra presupuesto tambien
	PR(502); // pra presupuesto tambien

	public Integer theState;

	private Comprobante(Integer aState) {
		this.theState = aState;
	}

	public static Comprobante value(Integer val) {
		Comprobante[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Comprobante value = var1[var3];
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
