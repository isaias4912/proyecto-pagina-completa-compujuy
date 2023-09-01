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
public enum Factura {
	FACTURA_ELECTRONICA(1),
	FACTURA_MANUAL(2),
	TICKET_FACTURA_FISCAL(3),
	TIKE_FACTURA_NO_VALIDA(20);

	public Integer theState;

	private Factura(Integer aState) {
		this.theState = aState;
	}

	public static Factura value(Integer val) {
		Factura[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Factura value = var1[var3];
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
