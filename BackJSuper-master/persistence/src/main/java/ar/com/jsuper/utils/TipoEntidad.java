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
public enum TipoEntidad {
	PERSONA(1),
	EMPRESA(2),
	TODAS(10);

	public Integer theState;

	private TipoEntidad(Integer aState) {
		this.theState = aState;
	}

	public static TipoEntidad value(Integer val) {
		TipoEntidad[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			TipoEntidad value = var1[var3];
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
