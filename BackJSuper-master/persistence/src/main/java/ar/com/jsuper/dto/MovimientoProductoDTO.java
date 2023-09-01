package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MovimientoProductoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private BigDecimal cantidad;

	private BigDecimal valorInicial;

	private BigDecimal valorFinal;

	private Integer tipo;
	
	private String nombreProducto;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date fecha;

	private DetMovimientosMinDTO movimiento;

	private CbteVenDetMinDTO venta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public DetMovimientosMinDTO getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(DetMovimientosMinDTO movimiento) {
		this.movimiento = movimiento;
	}

	public CbteVenDetMinDTO getVenta() {
		return venta;
	}

	public void setVenta(CbteVenDetMinDTO venta) {
		this.venta = venta;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

}
