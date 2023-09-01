package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "stock_sucursal")
public class StockSucursal implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private BigDecimal stockAnterior;
    @Column(name = "stock")
    private BigDecimal stock;
    @Column(name = "stock_minimo")
    private BigDecimal stockMinimo;
    @Column(name = "punto_reposicion")
    private BigDecimal puntoReposicion;
    @Column(name = "detalle")
    private String detalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursales_id")
    private Sucursales sucursal;
    @Column(name = "ubicacion")
    private String ubicacion;

    public StockSucursal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(BigDecimal stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

//    public Set<UbicacionStock> getUbicacionStocks() {
//        return ubicacionStocks;
//    }
//
//    public void setUbicacionStocks(Set<UbicacionStock> ubicacionStocks) {
//        this.ubicacionStocks = ubicacionStocks;
//    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getPuntoReposicion() {
        return puntoReposicion;
    }

    public void setPuntoReposicion(BigDecimal puntoReposicion) {
        this.puntoReposicion = puntoReposicion;
    }

    public BigDecimal getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(BigDecimal stockAnterior) {
        this.stockAnterior = stockAnterior;
    }
}
