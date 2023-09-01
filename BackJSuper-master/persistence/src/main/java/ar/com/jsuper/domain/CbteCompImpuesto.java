package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "cbte_comp_impuestos")
public class CbteCompImpuesto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "importe")
	private BigDecimal importe;

	@Column(name = "porcentaje")
	private BigDecimal porcentaje;

	@Column(name = "nombre")
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_comp_enc_id")
	private CbteCompEnc cbteCompEnc;

	@ManyToOne
	@JoinColumn(name = "impuestos_id")
	private Impuesto impuesto;

	public CbteCompImpuesto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CbteCompEnc getCbteCompEnc() {
		return cbteCompEnc;
	}

	public void setCbteCompEnc(CbteCompEnc cbteCompEnc) {
		this.cbteCompEnc = cbteCompEnc;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}

}
