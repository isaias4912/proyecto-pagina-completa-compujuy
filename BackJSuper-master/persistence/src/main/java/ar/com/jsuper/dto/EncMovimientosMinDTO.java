package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class EncMovimientosMinDTO implements Serializable {

	private Integer id;

	private String numero;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaCarga;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaIngreso;

	private String descripcion;

	private Integer tipo;

	private Integer subtipo;

	private SucursalesMinDTO sucursal;

	private CbteCompEncMinDTO facturaCompra;

	public EncMovimientosMinDTO() {
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public CbteCompEncMinDTO getFacturaCompra() {
		return facturaCompra;
	}

	public void setFacturaCompra(CbteCompEncMinDTO facturaCompra) {
		this.facturaCompra = facturaCompra;
	}

	public SucursalesMinDTO getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalesMinDTO sucursal) {
		this.sucursal = sucursal;
	}

}
