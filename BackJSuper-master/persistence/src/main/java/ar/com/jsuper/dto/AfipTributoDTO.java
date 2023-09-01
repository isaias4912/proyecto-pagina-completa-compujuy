package ar.com.jsuper.dto;

import ar.com.jsuper.utils.DoubleMoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

public class AfipTributoDTO implements Serializable {

	private Integer idAfipTpoTributo;
	private AfipSingleDTO tributo;
	private String descripcion;
	private String descAfipTpoTributo;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double alicuota;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double importe;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double baseImponible;

	public AfipSingleDTO getTributo() {
		return tributo;
	}

	public void setTributo(AfipSingleDTO tributo) {
		this.tributo = tributo;
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

}
