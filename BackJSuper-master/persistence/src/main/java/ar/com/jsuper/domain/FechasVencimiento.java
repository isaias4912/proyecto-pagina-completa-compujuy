package ar.com.jsuper.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

/**
 *
 * @author cilo22
 */
@Entity
@Table(name = "fechas_vencimiento")
public class FechasVencimiento implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicaciones_id")
    private Ubicacion ubicacion;
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "stock_sucursal_id", nullable = false)
    private StockSucursal stockSucursal;

    public FechasVencimiento() {
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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public StockSucursal getStockSucursal() {
        return stockSucursal;
    }

    public void setStockSucursal(StockSucursal stockSucursal) {
        this.stockSucursal = stockSucursal;
    }


}
