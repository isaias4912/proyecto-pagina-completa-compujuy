package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class EncMovimientosDTO implements Serializable {

	private Integer id;

	private String numero;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCarga;

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaIngreso;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaAceptacion;

	private String descripcion;

	private Integer tipo;

	private Integer subtipo;

	private SucursalesDTO sucursal;
	private SucursalesDTO sucursalOrigenDestino;

	private Set<DetMovimientosDTO> movimientos;

	private ProveedoresDTO proveedor;

	public EncMovimientosDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(Integer subtipo) {
		this.subtipo = subtipo;
	}

	public SucursalesDTO getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalesDTO sucursal) {
		this.sucursal = sucursal;
	}

	public Set<DetMovimientosDTO> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Set<DetMovimientosDTO> movimientos) {
		this.movimientos = movimientos;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public ProveedoresDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedoresDTO proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFechaAceptacion() {
		return fechaAceptacion;
	}

	public void setFechaAceptacion(Date fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}

	public SucursalesDTO getSucursalOrigenDestino() {
		return sucursalOrigenDestino;
	}

	public void setSucursalOrigenDestino(SucursalesDTO sucursalOrigenDestino) {
		this.sucursalOrigenDestino = sucursalOrigenDestino;
	}

}
