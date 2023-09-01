package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author RAFAEL
 */
public class ProductoPreviewDTO implements Serializable {

	private int id;
	private String nombre;
	private String nombreFinal;
	private BigDecimal contenidoNeto;
	private String detalle;
	private boolean activo;
	private BigDecimal iva;
	private BigDecimal precioCosto;
	private BigDecimal precioVenta;
	private ImagenProductoDTO imagenProducto;
	private ProductoPadreSinProductoDTO productoPadre;

	public ProductoPreviewDTO() {
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public BigDecimal getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(BigDecimal precioCosto) {
		this.precioCosto = precioCosto;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public ImagenProductoDTO getImagenProducto() {
		return imagenProducto;
	}

	public void setImagenProducto(ImagenProductoDTO imagenProducto) {
		this.imagenProducto = imagenProducto;
	}

	public ProductoPadreSinProductoDTO getProductoPadre() {
		return productoPadre;
	}

	public void setProductoPadre(ProductoPadreSinProductoDTO productoPadre) {
		this.productoPadre = productoPadre;
	}

}
