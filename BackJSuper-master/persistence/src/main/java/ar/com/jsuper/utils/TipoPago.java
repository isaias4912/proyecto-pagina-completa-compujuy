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
public enum TipoPago {
	EFECTIVO(1),
	TARJETA_CREDITO(2),
	TARJETA_DEBITO(3),
	CUENTA_CORRIENTE(4);

	public Integer theState;

	private TipoPago(Integer aState) {
		this.theState = aState;
	}

	public static TipoPago value(Integer val) {
		TipoPago[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			TipoPago value = var1[var3];
			if (value.toInt() == val) {
				return value;
			}
		}

		return null;
	}

	public Integer toInt() {
		return this.theState;
	}
}
