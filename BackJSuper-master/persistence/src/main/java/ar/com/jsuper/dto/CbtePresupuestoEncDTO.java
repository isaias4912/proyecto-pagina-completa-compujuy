/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author rafa22
 */
public class CbtePresupuestoEncDTO extends CbteEncDTO {

    private EstadoCbte estadoCbte;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVigencia;

    public EstadoCbte getEstadoCbte() {
        return estadoCbte;
    }

    public void setEstadoCbte(EstadoCbte estadoCbte) {
        this.estadoCbte = estadoCbte;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
}
