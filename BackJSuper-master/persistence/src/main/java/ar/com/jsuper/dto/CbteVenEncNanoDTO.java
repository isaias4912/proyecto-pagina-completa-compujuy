/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.MoneySerializer;
import ar.com.jsuper.utils.Origen;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rafa22
 */
public class CbteVenEncNanoDTO {

	private Integer id;
	private Comprobante tipoCbte;
	private String tipoVenta;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCarga;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCbte;
	private Date hora;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal saldo;
	private Integer idCliente;
	private Origen origen;
	private Boolean estado;
	private Boolean pagada;
	private SucursalesMinDTO sucursal;
	private String nombreCliente;
	private String direccionCliente;
	private Doc tipoDocCliente;
	private String nroDocCliente;
	private String cbteNro;
	private String ptoVenta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Comprobante getTipoCbte() {
		return tipoCbte;
	}

	public void setTipoCbte(Comprobante tipoCbte) {
		this.tipoCbte = tipoCbte;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getPagada() {
		return pagada;
	}

	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
	}

	public SucursalesMinDTO getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalesMinDTO sucursal) {
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

	public String getCbteNro() {
		return cbteNro;
	}

	public void setCbteNro(String cbteNro) {
		this.cbteNro = cbteNro;
	}

	public String getPtoVenta() {
		return ptoVenta;
	}

	public void setPtoVenta(String ptoVenta) {
		this.ptoVenta = ptoVenta;
	}

}
