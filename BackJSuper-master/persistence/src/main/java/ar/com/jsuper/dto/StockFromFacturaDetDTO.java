package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockFromFacturaDetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidadComprada;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidad;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidadAgregada;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal precioSinIva;

	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal precioConIva;

	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal subtotal;
    private BigDecimal maximo;
    private BigDecimal minimo;
    private Boolean completa;

    private BigDecimal iva;

    private Integer idProducto;

    private String nombreProducto;

    public StockFromFacturaDetDTO() {
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

    public BigDecimal getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public BigDecimal getPrecioConIva() {
        return precioConIva;
    }

    public void setPrecioConIva(BigDecimal precioConIva) {
        this.precioConIva = precioConIva;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getMaximo() {
        return maximo;
    }

    public void setMaximo(BigDecimal maximo) {
        this.maximo = maximo;
    }

    public BigDecimal getMinimo() {
        return minimo;
    }

    public void setMinimo(BigDecimal minimo) {
        this.minimo = minimo;
    }

    public BigDecimal getCantidadAgregada() {
        return cantidadAgregada;
    }

    public void setCantidadAgregada(BigDecimal cantidadAgregada) {
        this.cantidadAgregada = cantidadAgregada;
    }

    public Boolean getCompleta() {
        return completa;
    }

    public void setCompleta(Boolean completa) {
        this.completa = completa;
    }

    public BigDecimal getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(BigDecimal cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

}
