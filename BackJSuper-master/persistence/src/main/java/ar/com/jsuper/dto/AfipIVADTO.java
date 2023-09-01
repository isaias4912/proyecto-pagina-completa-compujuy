package ar.com.jsuper.dto;

import ar.com.jsuper.utils.DoubleMoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

public class AfipIVADTO implements Serializable {

	private Integer idAfipIva;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double alicuota;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double importe;
	@JsonSerialize(using = DoubleMoneySerializer.class)
	private Double baseImponible;
	private String descripcion;

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

}
