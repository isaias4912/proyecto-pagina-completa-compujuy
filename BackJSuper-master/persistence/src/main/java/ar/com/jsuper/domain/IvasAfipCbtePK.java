/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author rafa
 */
public class IvasAfipCbtePK implements Serializable {

	@Column(name = "tipo_cbte")
	private Integer tipoCbte;

	@Column(name = "id_cbte")
	private Integer cbte;

	@Column(name = "id_afip_iva")
	private Integer idAfipIva;

	public Integer getCbte() {
		return cbte;
	}

	public void setCbte(Integer cbte) {
		this.cbte = cbte;
	}

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

	@Override
	public int hashCode() {
		return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
	}
}
