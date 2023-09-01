package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rubros
 *
 */
@Entity
@Table(name = "productos_compuestos")
public class ProductosCompuestos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "producto_principal_id")
    private Producto productoPrincipal;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public ProductosCompuestos() {
        super();
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


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Producto getProductoPrincipal() {
        return productoPrincipal;
    }

    public void setProductoPrincipal(Producto productoPrincipal) {
        this.productoPrincipal = productoPrincipal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
