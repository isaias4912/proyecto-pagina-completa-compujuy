package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: mov
 *
 */
@Entity
@Table(name = "enc_movimientos")
public class EncMovimientos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fecha_carga")
	private Date fechaCarga;

	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "numero")
	private String numero;

	@Column(name = "tipo")
	private Integer tipo;

	@Column(name = "subtipo")
	private Integer subtipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursales_id", nullable = false)
	private Sucursales sucursal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proveedores_id")
	private Proveedores proveedor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_comp_enc_id")
	private CbteCompEnc facturaCompra;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	private Set<DetMovimientos> movimientos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pase_movimientos_id")
	private PaseMovimientos paseMovimiento;

	public EncMovimientos() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Sucursales getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursales sucursal) {
		this.sucursal = sucursal;
	}

	public Proveedores getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(Integer subtipo) {
		this.subtipo = subtipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Set<DetMovimientos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Set<DetMovimientos> movimientos) {
		this.movimientos = movimientos;
	}

	public CbteCompEnc getFacturaCompra() {
		return facturaCompra;
	}

	public void setFacturaCompra(CbteCompEnc facturaCompra) {
		this.facturaCompra = facturaCompra;
	}

	public PaseMovimientos getPaseMovimiento() {
		return paseMovimiento;
	}

	public void setPaseMovimiento(PaseMovimientos paseMovimiento) {
		this.paseMovimiento = paseMovimiento;
	}

}
