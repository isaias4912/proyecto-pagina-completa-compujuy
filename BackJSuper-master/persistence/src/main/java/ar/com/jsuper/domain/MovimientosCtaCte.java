package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movimientos_cta_cte")
public class MovimientosCtaCte implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "monto")
	private BigDecimal monto;
	@Column(name = "monto_total")
	private BigDecimal montoTotal;
	@Column(name = "saldo")
	private BigDecimal saldo;
	@Column(name = "interes")
	private BigDecimal interes;
	@Column(name = "fecha")
	private Date fecha;
	@Column(name = "pagado")
	private Boolean pagado;
	@Column(name = "descripcion")
	private String descripcion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuentas_corrientes_clientes_id", updatable = false)
	private CuentasCorrientes cuentaCorriente;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pagos_cta_cte_id")
	private PagosCtaCte pagoCtaCte;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pagos_cta_cte_saldo_id")
	private PagosCtaCte pagoCtaCteSaldo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pago_cbte_ven_id")
	private PagoCbteVen pagoVentas;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;

	public MovimientosCtaCte() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
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

	public CuentasCorrientes getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentasCorrientes cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public PagosCtaCte getPagoCtaCte() {
		return pagoCtaCte;
	}

	public void setPagoCtaCte(PagosCtaCte pagoCtaCte) {
		this.pagoCtaCte = pagoCtaCte;
	}

	public PagosCtaCte getPagoCtaCteSaldo() {
		return pagoCtaCteSaldo;
	}

	public void setPagoCtaCteSaldo(PagosCtaCte pagoCtaCteSaldo) {
		this.pagoCtaCteSaldo = pagoCtaCteSaldo;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public PagoCbteVen getPagoVentas() {
		return pagoVentas;
	}

	public void setPagoVentas(PagoCbteVen pagoVentas) {
		this.pagoVentas = pagoVentas;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

}
