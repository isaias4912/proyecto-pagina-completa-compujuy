package ar.com.jsuper.dto.reportes;

import ar.com.jsuper.dto.UnidadDTO;
import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author RAFAEL
 */
public class ProductoBarCodeDTO implements Serializable {

	private int id;
	private String nombre;
	private String nombreFinal;
	private BigDecimal contenidoNeto;
	private String detalle;
	private String codigoBarra;
	private BigDecimal precioCosto;
	private BigDecimal precioVenta;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precio;
	private Boolean pesable;
	private UnidadDTO unidad;
	private Integer tipo;
	private BigDecimal iva;
	private Boolean print;
	private String infoAdic;
	private String descripcion;

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

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
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

	public Boolean getPesable() {
		return pesable;
	}

	public void setPesable(Boolean pesable) {
		this.pesable = pesable;
	}

	public UnidadDTO getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadDTO unidad) {
		this.unidad = unidad;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Boolean getPrint() {
		return print;
	}

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public String getInfoAdic() {
		return infoAdic;
	}

	public void setInfoAdic(String infoAdic) {
		this.infoAdic = infoAdic;
	}

	public String getNombreFinal() {
		return nombreFinal;
	}

	public void setNombreFinal(String nombreFinal) {
		this.nombreFinal = nombreFinal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

}
