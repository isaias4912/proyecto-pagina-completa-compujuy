/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.utils.Comprobante;
import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class FilterFacturas {

    private String fechaInicial;
    private String fechaFinal;
    private BigDecimal totalMinimo;
    private BigDecimal totalMaximo;
    private SimpleObject proveedor;
    private Set<SimpleObject> sucursales;
    private Set<Comprobante> comprobantes;

    public FilterFacturas() {
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

    public SimpleObject getProveedor() {
        return proveedor;
    }

    public void setProveedor(SimpleObject proveedor) {
        this.proveedor = proveedor;
    }

    public Set<SimpleObject> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<SimpleObject> sucursales) {
        this.sucursales = sucursales;
    }

    public Set<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(Set<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

}
