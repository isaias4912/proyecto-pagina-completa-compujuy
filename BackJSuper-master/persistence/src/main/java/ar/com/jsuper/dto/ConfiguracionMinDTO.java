package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConfiguracionMinDTO implements Serializable {

	private Integer id;
	private BigDecimal cliCtaCteInteres = BigDecimal.ZERO;
	private BigDecimal iva = BigDecimal.ZERO;
	private BigDecimal margenGanancia = BigDecimal.ZERO;
	private String pais = "ARGENTINA";
	private String logoReporte = "";
	private String encabezadoReporte = "";
	private String cuitEmpresa;
	private TipoEmpresa tipoEmpresa;
	private Boolean existeCertAfip;
	private String razonSocial;
	private String domicilioComercial;
	private String ingresosBrutos;
	private String printHost;
	private Integer printPort;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaIniAct;

	public ConfiguracionMinDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCliCtaCteInteres() {
		return cliCtaCteInteres;
	}

	public void setCliCtaCteInteres(BigDecimal cliCtaCteInteres) {
		this.cliCtaCteInteres = cliCtaCteInteres;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getMargenGanancia() {
		return margenGanancia;
	}

	public void setMargenGanancia(BigDecimal margenGanancia) {
		this.margenGanancia = margenGanancia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLogoReporte() {
		return logoReporte;
	}

	public void setLogoReporte(String logoReporte) {
		this.logoReporte = logoReporte;
	}

	public String getEncabezadoReporte() {
		return encabezadoReporte;
	}

	public void setEncabezadoReporte(String encabezadoReporte) {
		this.encabezadoReporte = encabezadoReporte;
	}

	public String getCuitEmpresa() {
		return cuitEmpresa;
	}

	public void setCuitEmpresa(String cuitEmpresa) {
		this.cuitEmpresa = cuitEmpresa;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public Boolean getExisteCertAfip() {
		return existeCertAfip;
	}

	public void setExisteCertAfip(Boolean existeCertAfip) {
		this.existeCertAfip = existeCertAfip;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDomicilioComercial() {
		return domicilioComercial;
	}

	public void setDomicilioComercial(String domicilioComercial) {
		this.domicilioComercial = domicilioComercial;
	}

	public String getIngresosBrutos() {
		return ingresosBrutos;
	}

	public void setIngresosBrutos(String ingresosBrutos) {
		this.ingresosBrutos = ingresosBrutos;
	}

	public Date getFechaIniAct() {
		return fechaIniAct;
	}

	public void setFechaIniAct(Date fechaIniAct) {
		this.fechaIniAct = fechaIniAct;
	}

	public String getPrintHost() {
		return printHost;
	}

	public void setPrintHost(String printHost) {
		this.printHost = printHost;
	}

	public Integer getPrintPort() {
		return printPort;
	}

	public void setPrintPort(Integer printPort) {
		this.printPort = printPort;
	}
}
