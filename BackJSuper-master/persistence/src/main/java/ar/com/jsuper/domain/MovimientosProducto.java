package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: mov
 *
 */
@Entity
@Table(name = "movimientos_producto")
public class MovimientosProducto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cantidad")
	private BigDecimal cantidad;

	@Column(name = "valor_inicial")
	private BigDecimal valorInicial;

	@Column(name = "valor_final")
	private BigDecimal valorFinal;

	@Column(name = "tipo")
	private Integer tipo;

	@Column(name = "fecha")
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "det_movimientos_id")
	private DetMovimientos movimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_det_id")
	private CbteDet venta;

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

	public DetMovimientos getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(DetMovimientos movimiento) {
		this.movimiento = movimiento;
	}

	public CbteDet getVenta() {
		return venta;
	}

	public void setVenta(CbteDet venta) {
		this.venta = venta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
