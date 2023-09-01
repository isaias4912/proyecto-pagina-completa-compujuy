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
public enum TipoCliente {
	SIN_ESPECIFICAR(0),
	MONOTRIBUTO(1),
	RESPONSABLE_INSCRIPTO(2),
	EXCENTO(3),
	CONSUMIDOR_FINAL(4),
	OTRO(20);

	public Integer theState;

	private TipoCliente(Integer aState) {
		this.theState = aState;
	}

	public static TipoCliente value(Integer val) {
		TipoCliente[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			TipoCliente value = var1[var3];
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
