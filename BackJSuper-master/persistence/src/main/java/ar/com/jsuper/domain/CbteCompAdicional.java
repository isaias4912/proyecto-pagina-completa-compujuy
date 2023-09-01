package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "cbte_comp_adicional")
public class CbteCompAdicional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "importe")
	private BigDecimal importe;

	@Column(name = "nombre")
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_comp_enc_id")
	private CbteCompEnc cbteCompEnc;

	public CbteCompAdicional() {
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

}
