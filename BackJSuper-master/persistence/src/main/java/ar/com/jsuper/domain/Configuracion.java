package ar.com.jsuper.domain;

import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEmpresaConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "configuracion")
public class Configuracion implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "cli_ctacte_interes")
	private BigDecimal cliCtaCteInteres = BigDecimal.ZERO;
	@Column(name = "iva")
	private BigDecimal iva = BigDecimal.ZERO;
	@Column(name = "margen_ganancia")
	private BigDecimal margenGanancia = BigDecimal.ZERO;
	@Column(name = "pais")
	private String pais = "ARGENTINA";
	@Column(name = "logo_reporte")
	private String logoReporte = "";
	@Column(name = "encabezado_reporte")
	private String encabezadoReporte = "";
	@Column(name = "cuit_empresa")
	private String cuitEmpresa;
	@Column(name = "razon_social")
	private String razonSocial;
	@Column(name = "domicilio_comercial")
	private String domicilioComercial;
	@Column(name = "ingresos_brutos")
	private String ingresosBrutos;
	@Column(name = "fecha_ini_act")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date fechaIniAct;
	@Convert(converter = TipoEmpresaConverter.class)
	@Column(name = "tipo_empresa")
	private TipoEmpresa tipoEmpresa;
	@Column(name = "cert_o")
	private String certO;
	@Column(name = "cert_cn")
	private String certCN;
	@Column(name = "cert_serial_number")
	private String certSerialNumber;
	@Column(name = "cert_password")
	private String certPassword;
	@Column(name = "cert_name_crt")
	private String certNameCRT;
	@Column(name = "afip_produccion")
	private Boolean afipProduccion;
	@Column(name = "cut_desc_ticket")
	private Boolean cutDescTicket;
	@Column(name = "size_desc_ticket")
	private Integer sizeDescTicket;
	@Column(name = "backup_hab")
	private Boolean backupHab;
	@Column(name = "backup_cron")
	private String backupCron;
	@Column(name = "enabled_venta")
	private Boolean enabledVenta;
	@OneToMany(mappedBy = "configuracion", fetch = FetchType.LAZY)
	private Set<PuntoVenta> puntosVenta;
	@Column(name = "print_host")
	private String printHost;
	@Column(name = "print_port")
	private String printPort;

	public Configuracion() {
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

	public Set<PuntoVenta> getPuntosVenta() {
		return puntosVenta;
	}

	public void setPuntosVenta(Set<PuntoVenta> puntosVenta) {
		this.puntosVenta = puntosVenta;
	}

	public String getPrintHost() {
		return printHost;
	}

	public void setPrintHost(String printHost) {
		this.printHost = printHost;
	}

	public String getPrintPort() {
		return printPort;
	}

	public void setPrintPort(String printPort) {
		this.printPort = printPort;
	}
}
