/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "formapagos")
public class FormaPagos implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "detalle")
	private String detalle;
	@Column(name = "proveedor")
	private boolean proveedor;
	@Column(name = "cliente")
	private boolean cliente;
	@Column(name = "fac_elec")
	private boolean facElec;
	@Column(name = "activo")
	private boolean activo;
	@Column(name = "cta_cte_cli")
	private boolean ctaCteCli;
	@Column(name = "cta_cte_prov")
	private boolean ctaCteProv;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return this.descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isProveedor() {
		return proveedor;
	}

	public void setProveedor(boolean proveedor) {
		this.proveedor = proveedor;
	}

	public boolean isFacElec() {
		return facElec;
	}

	public void setFacElec(boolean facElec) {
		this.facElec = facElec;
	}

	public boolean isCtaCteCli() {
		return ctaCteCli;
	}

	public void setCtaCteCli(boolean ctaCteCli) {
		this.ctaCteCli = ctaCteCli;
	}

	public boolean isCtaCteProv() {
		return ctaCteProv;
	}

	public void setCtaCteProv(boolean ctaCteProv) {
		this.ctaCteProv = ctaCteProv;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

}
