/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.utils.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author rafa22
 */
@Entity
@DiscriminatorValue(value = "3")
public class CbteEncPresupuesto extends CbteEnc implements Serializable {

    @Convert(converter = EstadoCbteConverter.class)
    @Column(name = "estado_cbte")
    private EstadoCbte estadoCbte;
    @Column(name = "fecha_vigencia")
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
