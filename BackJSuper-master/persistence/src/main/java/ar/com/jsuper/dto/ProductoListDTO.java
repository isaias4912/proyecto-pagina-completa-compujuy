package ar.com.jsuper.dto;

//import ar.com.jsuper.dto.maps.BigDecimalSerializer;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ar.com.jsuper.domain.ImagenProducto;
import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author RAFAEL
 */
public class ProductoListDTO extends ObjectDTO implements Serializable, IProductoPrecio {

    private int id;
    private String nombre;
    private String nombreFinal;
    private BigDecimal contenidoNeto;
    private String detalle;
    private boolean activo;
    private String codigoEspecial;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal precioCosto;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal precioVenta;
    private Boolean pesable;
    private Boolean ingresoCantidadManual;
    private UnidadDTO unidad;
    private Integer tipo;
    private BigDecimal iva;
    private Set<CodigoBarrasDTO> codigoBarras;
    private ProductoPadreSinProductoDTO productoPadre;
    private Set<ProductosCompuestosDTO> productosCompuestos;
    private List<ListaPreciosDTO> precios;
    private ImagenProductoDTO imagen;

    public ProductoListDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreFinal() {
        return nombreFinal;
    }

    public void setNombreFinal(String nombreFinal) {
        this.nombreFinal = nombreFinal;
    }

    public BigDecimal getContenidoNeto() {
        return contenidoNeto;
    }

    public void setContenidoNeto(BigDecimal contenidoNeto) {
        this.contenidoNeto = contenidoNeto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public UnidadDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadDTO unidad) {
        this.unidad = unidad;
    }

    public Set<CodigoBarrasDTO> getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(Set<CodigoBarrasDTO> codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public ProductoPadreSinProductoDTO getProductoPadre() {
        return productoPadre;
    }

    public void setProductoPadre(ProductoPadreSinProductoDTO productoPadre) {
        this.productoPadre = productoPadre;
    }

    public Set<ProductosCompuestosDTO> getProductosCompuestos() {
        return productosCompuestos;
    }

    public void setProductosCompuestos(Set<ProductosCompuestosDTO> productosCompuestos) {
        this.productosCompuestos = productosCompuestos;
    }

    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }

    @Override
    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    @Override
    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Boolean getPesable() {
        return pesable;
    }

    public void setPesable(Boolean pesable) {
        this.pesable = pesable;
    }

    public Boolean getIngresoCantidadManual() {
        return ingresoCantidadManual;
    }

    public void setIngresoCantidadManual(Boolean ingresoCantidadManual) {
        this.ingresoCantidadManual = ingresoCantidadManual;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getCodigoEspecial() {
        return codigoEspecial;
    }

    public void setCodigoEspecial(String codigoEspecial) {
        this.codigoEspecial = codigoEspecial;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    @Override
    public List<ListaPreciosDTO> getPrecios() {
        return precios;
    }

    @Override
    public void setPrecios(List<ListaPreciosDTO> precios) {
        this.precios = precios;
    }

    public ImagenProductoDTO getImagen() {
        return imagen;
    }

    public void setImagen(ImagenProductoDTO imagen) {
        this.imagen = imagen;
    }
}
