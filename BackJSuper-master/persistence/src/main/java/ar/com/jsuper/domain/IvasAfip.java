package ar.com.jsuper.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "ivas_afip")
@IdClass(IvasAfipCbtePK.class)
public class IvasAfip implements Serializable {

	@Id
	@Column(name = "tipo_cbte")
	private Integer tipoCbte;
	@Id
	@Column(name = "id_afip_iva")
	private Integer idAfipIva;
	@Column(name = "alicuota")
	private Double alicuota;
	@Column(name = "importe")
	private Double importe;
	@Column(name = "base_imponible")
	private Double baseImponible;
	@Column(name = "descripcion")
	private String descripcion;
	@Id
    @Column(name = "id_cbte")
	private Integer cbte;

	public Integer getTipoCbte() {
		return tipoCbte;
	}

	public void setTipoCbte(Integer tipoCbte) {
		this.tipoCbte = tipoCbte;
	}

	public Integer getIdAfipIva() {
		return idAfipIva;
	}

	public void setIdAfipIva(Integer idAfipIva) {
		this.idAfipIva = idAfipIva;
	}

	public Double getAlicuota() {
		return alicuota;
	}

	public void setAlicuota(Double alicuota) {
		this.alicuota = alicuota;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCbte() {
		return cbte;
	}

	public void setCbte(Integer cbte) {
		this.cbte = cbte;
	}

}
