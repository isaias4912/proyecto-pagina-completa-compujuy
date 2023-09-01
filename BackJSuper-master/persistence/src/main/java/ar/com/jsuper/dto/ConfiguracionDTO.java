package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class ConfiguracionDTO implements Serializable {

	private Integer id;
	private BigDecimal cliCtaCteInteres = BigDecimal.ZERO;
	private BigDecimal iva = BigDecimal.ZERO;
	private BigDecimal margenGanancia = BigDecimal.ZERO;
	private String pais = "ARGENTINA";
	private String logoReporte = "";
	private String encabezadoReporte = "";
	private String cuitEmpresa;
	private TipoEmpresa tipoEmpresa;
	private String razonSocial;
	private String domicilioComercial;
	private String ingresosBrutos;
	private String privateKey;
	private String certO;
	private String certCN;
	private String certSerialNumber;
	private String certPassword;
	private String certNameCRT;
	private Boolean fileCertificateCRT;
	private Boolean fileCertificateCSR;
	private Boolean filePrivateKey;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaIniAct;
	private Boolean afipProduccion;
	private Boolean cutDescTicket;
	private Integer sizeDescTicket;
	private Boolean backupHab;
	private String backupCron;
	private Boolean enabledVenta;
	private Set<PuntoVentaDTO> puntosVenta;
	private String printHost;
	private Integer printPort;
	
	public ConfiguracionDTO() {
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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getCertO() {
		return certO;
	}

	public void setCertO(String certO) {
		this.certO = certO;
	}

	public String getCertCN() {
		return certCN;
	}

	public void setCertCN(String certCN) {
		this.certCN = certCN;
	}

	public String getCertSerialNumber() {
		return certSerialNumber;
	}

	public void setCertSerialNumber(String certSerialNumber) {
		this.certSerialNumber = certSerialNumber;
	}

	public String getCertPassword() {
		return certPassword;
	}

	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}

	public String getCertNameCRT() {
		return certNameCRT;
	}

	public void setCertNameCRT(String certNameCRT) {
		this.certNameCRT = certNameCRT;
	}

	public Boolean getFileCertificateCRT() {
		return fileCertificateCRT;
	}

	public void setFileCertificateCRT(Boolean fileCertificateCRT) {
		this.fileCertificateCRT = fileCertificateCRT;
	}

	public Boolean getFileCertificateCSR() {
		return fileCertificateCSR;
	}

	public void setFileCertificateCSR(Boolean fileCertificateCSR) {
		this.fileCertificateCSR = fileCertificateCSR;
	}

	public Boolean getFilePrivateKey() {
		return filePrivateKey;
	}

	public void setFilePrivateKey(Boolean filePrivateKey) {
		this.filePrivateKey = filePrivateKey;
	}

	public Boolean getAfipProduccion() {
		return afipProduccion;
	}

	public void setAfipProduccion(Boolean afipProduccion) {
		this.afipProduccion = afipProduccion;
	}

	public Boolean getCutDescTicket() {
		return cutDescTicket;
	}

	public void setCutDescTicket(Boolean cutDescTicket) {
		this.cutDescTicket = cutDescTicket;
	}

	public Integer getSizeDescTicket() {
		return sizeDescTicket;
	}

	public void setSizeDescTicket(Integer sizeDescTicket) {
		this.sizeDescTicket = sizeDescTicket;
	}

	public Boolean getBackupHab() {
		return backupHab;
	}

	public void setBackupHab(Boolean backupHab) {
		this.backupHab = backupHab;
	}

	public String getBackupCron() {
		return backupCron;
	}

	public void setBackupCron(String backupCron) {
		this.backupCron = backupCron;
	}

	public Boolean getEnabledVenta() {
		return enabledVenta;
	}

	public void setEnabledVenta(Boolean enabledVenta) {
		this.enabledVenta = enabledVenta;
	}

	public Set<PuntoVentaDTO> getPuntosVenta() {
		return puntosVenta;
	}

	public void setPuntosVenta(Set<PuntoVentaDTO> puntosVenta) {
		this.puntosVenta = puntosVenta;
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
