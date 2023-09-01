/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.math.BigDecimal;

/**
 *
 * @author rafa22
 */
public class FilterTransaccionDTO {

    private String fechaInicial;
    private String fechaFinal;
    private Integer id;
    private Integer caja;
    private SimpleObjectDTO usuario;
    private Integer transaccion;
    private BigDecimal totalMinimo;
    private BigDecimal totalMaximo;

    public FilterTransaccionDTO() {
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

    public Integer getCaja() {
        return caja;
    }

    public void setCaja(Integer caja) {
        this.caja = caja;
    }

    public SimpleObjectDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(SimpleObjectDTO usuario) {
        this.usuario = usuario;
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

    public Integer getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Integer transaccion) {
        this.transaccion = transaccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
