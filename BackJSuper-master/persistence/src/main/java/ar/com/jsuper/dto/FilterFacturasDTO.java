/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.domain.utils.*;
import java.math.BigDecimal;

/**
 *
 * @author rafa22
 */
public class FilterFacturasDTO {

    private String fechaInicial;
    private String fechaFinal;
    private BigDecimal totalMinimo;
    private BigDecimal totalMaximo;
    private SimpleObjectDTO proveedor;

    public FilterFacturasDTO() {
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getTotalMinimo() {
        return totalMinimo;
    }

    public void setTotalMinimo(BigDecimal totalMinimo) {
        this.totalMinimo = totalMinimo;
    }

    public BigDecimal getTotalMaximo() {
        return totalMaximo;
    }

    public void setTotalMaximo(BigDecimal totalMaximo) {
        this.totalMaximo = totalMaximo;
    }

    public SimpleObjectDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(SimpleObjectDTO proveedor) {
        this.proveedor = proveedor;
    }

}
