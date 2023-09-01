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
public enum Asociada {
	TRANSACCION(1);

	public Integer theState;

	private Asociada(Integer aState) {
		this.theState = aState;
	}

	public static Asociada value(Integer val) {
		Asociada[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			Asociada value = var1[var3];
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
