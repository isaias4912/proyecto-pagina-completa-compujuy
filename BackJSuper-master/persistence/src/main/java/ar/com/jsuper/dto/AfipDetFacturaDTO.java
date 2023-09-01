package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AfipDetFacturaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Double cantidad;

    private Double precioSinIva;

    private Double precioConIva;

    private Double subtotal;

    private Double baseImponible;

    private AfipSingleDTO iva;
    
    private Double importeIva;

    private Integer idProducto;

    private String nombreProducto;

    private String servicio;
    
    private ProductoMinDTO producto;

    public AfipDetFacturaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(Double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public Double getPrecioConIva() {
        return precioConIva;
    }

    public void setPrecioConIva(Double precioConIva) {
        this.precioConIva = precioConIva;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public AfipSingleDTO getIva() {
        return iva;
    }

    public void setIva(AfipSingleDTO iva) {
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Double getImporteIva() {
        return importeIva;
    }

    public void setImporteIva(Double importeIva) {
        this.importeIva = importeIva;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

}
