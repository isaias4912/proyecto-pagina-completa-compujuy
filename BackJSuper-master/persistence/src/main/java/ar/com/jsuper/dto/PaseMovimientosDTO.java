package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity implementation class for Entity: mov
 *
 */
public class PaseMovimientosDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fecha;
	private String descripcion;
	private Boolean estado;
	private BigDecimal cantidad;
	private SucursalesMinDTO sucursalDestino;
	private SucursalesMinDTO sucursalOrigen;
	private ProductoMinDTO producto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public SucursalesMinDTO getSucursalDestino() {
		return sucursalDestino;
	}

	public void setSucursalDestino(SucursalesMinDTO sucursalDestino) {
		this.sucursalDestino = sucursalDestino;
	}

	public SucursalesMinDTO getSucursalOrigen() {
		return sucursalOrigen;
	}

	public void setSucursalOrigen(SucursalesMinDTO sucursalOrigen) {
		this.sucursalOrigen = sucursalOrigen;
	}

	public ProductoMinDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoMinDTO producto) {
		this.producto = producto;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

}
