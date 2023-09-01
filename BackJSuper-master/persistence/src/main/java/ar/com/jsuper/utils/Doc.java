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
public enum Doc {
	CUIT(80),
	CUIL(86),
	CDI(87),
	LE(89),
	LC(90),
	DNI(96);

	public Integer theState;

	private Doc(Integer aState) {
		this.theState = aState;
	}

	public static Doc value(Integer val) {
		Doc[] var1 = values();
		Integer var2 = var1.length;

		for (Integer var3 = 0; var3 < var2; ++var3) {
			Doc value = var1[var3];
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
