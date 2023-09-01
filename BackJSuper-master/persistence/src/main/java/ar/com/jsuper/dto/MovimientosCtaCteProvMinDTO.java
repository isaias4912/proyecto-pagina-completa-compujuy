package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientosCtaCteProvMinDTO implements Serializable {

	private Integer id;
	private Integer tipo;
	private BigDecimal monto;
	private BigDecimal montoTotal;
	private BigDecimal saldo;
	private BigDecimal saldoActual;
	private BigDecimal paga;
	private Boolean pagadoConfirm;
	private BigDecimal interes;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;
	private Boolean pagado;
	private String descripcion;
	private PagosCtaCteNanoDTO pagoCtaCte;

	public MovimientosCtaCteProvMinDTO() {
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}

	public BigDecimal getPaga() {
		return paga;
	}

	public void setPaga(BigDecimal paga) {
		this.paga = paga;
	}

	public Boolean getPagadoConfirm() {
		return pagadoConfirm;
	}

	public void setPagadoConfirm(Boolean pagadoConfirm) {
		this.pagadoConfirm = pagadoConfirm;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PagosCtaCteNanoDTO getPagoCtaCte() {
		return pagoCtaCte;
	}

	public void setPagoCtaCte(PagosCtaCteNanoDTO pagoCtaCte) {
		this.pagoCtaCte = pagoCtaCte;
	}

}
