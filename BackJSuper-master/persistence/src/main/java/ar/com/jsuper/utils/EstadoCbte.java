/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

/**
 * @author rafa
 */
public enum EstadoCbte {
    PRES_PENDIENTE(11),
    PRES_APROBADO(12); // pra presupuesto tambien

    public Integer theState;

    private EstadoCbte(Integer aState) {
        this.theState = aState;
    }

    public static EstadoCbte value(Integer val) {
        EstadoCbte[] var1 = values();
        Integer var2 = var1.length;

        for (Integer var3 = 0; var3 < var2; ++var3) {
            EstadoCbte value = var1[var3];
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
