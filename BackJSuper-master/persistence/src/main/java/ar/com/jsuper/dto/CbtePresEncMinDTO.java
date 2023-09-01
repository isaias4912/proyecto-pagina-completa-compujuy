/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.EstadoCbte;
import ar.com.jsuper.utils.Factura;
import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author rafa22
 */
public class CbtePresEncMinDTO extends CbteEncMinDTO {
    private Comprobante tipoCbte;
    private EstadoCbte estadoCbte;

    public EstadoCbte getEstadoCbte() {
        return estadoCbte;
    }

    public void setEstadoCbte(EstadoCbte estadoCbte) {
        this.estadoCbte = estadoCbte;
    }

    public Comprobante getTipoCbte() {
        return tipoCbte;
    }

    public void setTipoCbte(Comprobante tipoCbte) {
        this.tipoCbte = tipoCbte;
    }
}
