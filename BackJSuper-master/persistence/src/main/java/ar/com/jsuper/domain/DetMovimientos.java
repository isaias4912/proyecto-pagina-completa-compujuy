package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: mov
 *
 */
@Entity
@Table(name = "det_movimientos")
public class DetMovimientos implements Serializable {

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

	@Column(name = "referencia")
	private String referencia;

//    @Column(name = "origen")
//    private Integer origen;
	@Column(name = "origen_producto_id")
	private Integer origenProductoId;

	@Column(name = "id_producto")
	private Integer idProducto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enc_movimientos_id")
	private EncMovimientos factura;

	public DetMovimientos() {
	}

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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public EncMovimientos getFactura() {
		return factura;
	}

	public void setFactura(EncMovimientos factura) {
		this.factura = factura;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

//    public Integer getOrigen() {
//        return origen;
//    }
//
//    public void setOrigen(Integer origen) {
//        this.origen = origen;
//    }
	public Integer getOrigenProductoId() {
		return origenProductoId;
	}

	public void setOrigenProductoId(Integer origenProductoId) {
		this.origenProductoId = origenProductoId;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

}
