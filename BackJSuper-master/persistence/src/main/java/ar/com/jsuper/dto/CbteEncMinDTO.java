/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class CbteEncMinDTO {

	private int id;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCarga;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCbte;
	private Date hora;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;
	private Integer idCliente;
	private Origen origen;
	private UsuariosNanoDTO usuario;
	private SimpleObjectDTO sucursal;
	private String nombreCliente;
	private String direccionCliente;
	private Doc tipoDocCliente;
	private String nroDocCliente;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaCbte() {
		return fechaCbte;
	}

	public void setFechaCbte(Date fechaCbte) {
		this.fechaCbte = fechaCbte;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Origen getOrigen() {
		return origen;
	}

	public void setOrigen(Origen origen) {
		this.origen = origen;
	}

	public UsuariosNanoDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosNanoDTO usuario) {
		this.usuario = usuario;
	}

	public SimpleObjectDTO getSucursal() {
		return sucursal;
	}

	public void setSucursal(SimpleObjectDTO sucursal) {
		this.sucursal = sucursal;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public Doc getTipoDocCliente() {
		return tipoDocCliente;
	}

	public void setTipoDocCliente(Doc tipoDocCliente) {
		this.tipoDocCliente = tipoDocCliente;
	}

	public String getNroDocCliente() {
		return nroDocCliente;
	}

	public void setNroDocCliente(String nroDocCliente) {
		this.nroDocCliente = nroDocCliente;
	}
}
