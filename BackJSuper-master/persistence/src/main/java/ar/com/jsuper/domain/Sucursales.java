package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sucursales")
public class Sucursales implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "prefijo_tel_fijo")
    private String prefijoTelFijo;
    @Column(name = "numero_tel_fijo")
    private String numeroTelFijo;
    @Column(name = "prefijo_tel_cel")
    private String prefijoTelCel;
    @Column(name = "numero_tel_cel")
    private String numeroTelCel;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "activo")
    private boolean activo;
    @Column(name = "principal")
    private boolean principal;
    @Column(name = "key_sucursal")
    private String keyid;
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StockSucursal> stockSucursales;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", updatable = false)
    private App app;
    @ManyToMany(mappedBy = "sucursales", fetch = FetchType.LAZY)
    private Set<Usuarios> usuarios = new HashSet<>();

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return this.id + " " + this.nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<StockSucursal> getStockSucursales() {
        return stockSucursales;
    }

    public void setStockSucursales(Set<StockSucursal> stockSucursales) {
        this.stockSucursales = stockSucursales;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getPrefijoTelFijo() {
        return prefijoTelFijo;
    }

    public void setPrefijoTelFijo(String prefijoTelFijo) {
        this.prefijoTelFijo = prefijoTelFijo;
    }

    public String getNumeroTelFijo() {
        return numeroTelFijo;
    }

    public void setNumeroTelFijo(String numeroTelFijo) {
        this.numeroTelFijo = numeroTelFijo;
    }

    public String getPrefijoTelCel() {
        return prefijoTelCel;
    }

    public void setPrefijoTelCel(String prefijoTelCel) {
        this.prefijoTelCel = prefijoTelCel;
    }

    public String getNumeroTelCel() {
        return numeroTelCel;
    }

    public void setNumeroTelCel(String numeroTelCel) {
        this.numeroTelCel = numeroTelCel;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public Set<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

}
