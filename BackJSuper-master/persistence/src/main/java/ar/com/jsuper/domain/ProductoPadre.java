package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author RAFAEL
 */
@Entity
@Table(name = "producto_padre")
public class ProductoPadre implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nombre_corto")
    private String nombreCorto;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "activo")
    private boolean activo;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familias_id")
    private Familias familia;
    @ManyToOne
    @JoinColumn(name = "app_id")
    private App app;
    @OneToMany(mappedBy = "productoPadre", fetch = FetchType.LAZY)
    private Set<Producto> productos;

    public ProductoPadre() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

//    public Rubros getRubro() {
//        return rubro;
//    }
//
//    public void setRubro(Rubros rubro) {
//        this.rubro = rubro;
//    }
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Familias getFamilia() {
        return familia;
    }

    public void setFamilia(Familias familia) {
        this.familia = familia;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
