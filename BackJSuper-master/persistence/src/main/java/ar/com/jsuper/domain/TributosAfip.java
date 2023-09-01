package ar.com.jsuper.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tributos_afip")
@IdClass(TributosAfipCbtePK.class)
public class TributosAfip implements Serializable {

	@Id
	@Column(name = "tipo_cbte")
	private Integer tipoCbte;
	@Id
	@Column(name = "ind")
	private Integer index;
	@Column(name = "id_afip_tpo_tributo")
	private Integer idAfipTpoTributo;
	@Column(name = "desc_afip_tpo_tributo")
	private String descAfipTpoTributo;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "alicuota")
	private Double alicuota;
	@Column(name = "importe")
	private Double importe;
	@Column(name = "base_imponible")
	private Double baseImponible;
	@Id
	@Column(name = "id_cbte")
	private Integer cbte;

	public Integer getTipoCbte() {
		return tipoCbte;
	}

	public void setTipoCbte(Integer tipoCbte) {
		this.tipoCbte = tipoCbte;
	}

	public Integer getCbte() {
		return cbte;
	}

	public void setCbte(Integer cbte) {
		this.cbte = cbte;
	}

	public Integer getIdAfipTpoTributo() {
		return idAfipTpoTributo;
	}

	public void setIdAfipTpoTributo(Integer idAfipTpoTributo) {
		this.idAfipTpoTributo = idAfipTpoTributo;
	}

	public String getDescAfipTpoTributo() {
		return descAfipTpoTributo;
	}

	public void setDescAfipTpoTributo(String descAfipTpoTributo) {
		this.descAfipTpoTributo = descAfipTpoTributo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
