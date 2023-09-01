package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;

public class DetMovimientosDTO implements Serializable {

    private Integer id;
    private BigDecimal cantidad;
    private String referencia;
    private Integer tipo;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal valorInicial;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal valorFinal;
    private ProductoMinDTO producto;

    public DetMovimientosDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

}
