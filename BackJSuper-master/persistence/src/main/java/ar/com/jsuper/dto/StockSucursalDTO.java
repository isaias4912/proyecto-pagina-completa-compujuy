package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Set;

public class StockSucursalDTO extends ObjectDTO implements Serializable {

    private int id;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal stock;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal stockMinimo;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal puntoReposicion;
    private String detalle;
    private String ubicacion;
//    @JsonBackReference
    @JsonIgnoreProperties("stockSucursales")
    private ProductoDTO productoDTO;
//    @JsonBackReference
//    @JsonIgnoreProperties("stockSucursales")
    private SucursalesDTO sucursal;
////    @JsonManagedReference
    @JsonIgnoreProperties("stockSucursales")
    private Set<UbicacionStockDTO> ubicacionStocks;

    public StockSucursalDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public ProductoDTO getProductoDTO() {
        return productoDTO;
    }

    public void setProductoDTO(ProductoDTO productoDTO) {
        this.productoDTO = productoDTO;
    }

    public SucursalesDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalesDTO sucursal) {
        this.sucursal = sucursal;
    }

    public Set<UbicacionStockDTO> getUbicacionStocks() {
        return ubicacionStocks;
    }

    public void setUbicacionStocks(Set<UbicacionStockDTO> ubicacionStocks) {
        this.ubicacionStocks = ubicacionStocks;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public BigDecimal getPuntoReposicion() {
        return puntoReposicion;
    }

    public void setPuntoReposicion(BigDecimal puntoReposicion) {
        this.puntoReposicion = puntoReposicion;
    }

}
