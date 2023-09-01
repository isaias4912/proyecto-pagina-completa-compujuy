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
@Table(name = "pase_movimientos")
public class PaseMovimientos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "cantidad")
	private BigDecimal cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursales_destino_id", nullable = false)
	private Sucursales sucursalDestino;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursales_origen_id", nullable = false)
	private Sucursales sucursalOrigen;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", updatable = false)
	private App app;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Sucursales getSucursalDestino() {
		return sucursalDestino;
	}

	public void setSucursalDestino(Sucursales sucursalDestino) {
		this.sucursalDestino = sucursalDestino;
	}

	public Sucursales getSucursalOrigen() {
		return sucursalOrigen;
	}

	public void setSucursalOrigen(Sucursales sucursalOrigen) {
		this.sucursalOrigen = sucursalOrigen;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

}
