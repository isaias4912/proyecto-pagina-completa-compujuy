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
public enum TipoEmpresa {
	SIN_ESPECIFICAR(0),
	MONOTRIBUTO(1),
	RESPONSABLE_INSCRIPTO(2),
	EXCENTO(3),
	OTRO(20);

	public Integer theState;

	private TipoEmpresa(Integer aState) {
		this.theState = aState;
	}

	public static TipoEmpresa value(Integer val) {
		TipoEmpresa[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			TipoEmpresa value = var1[var3];
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
