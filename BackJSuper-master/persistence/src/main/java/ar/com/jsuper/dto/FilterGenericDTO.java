/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.util.Set;

/**
 *
 * @author rafa22
 */
public class FilterGenericDTO {

	private Integer id;
	private String text;
	private String nombre;
	private String fechaInicial;
	private String fechaFinal;
	private Integer ultimos;
	private Integer activo;
	private Integer valido;
	private Integer alerta;
	private Boolean ventas;
	private Boolean asociados;
	private ProductoListDTO producto;
	private Set<Integer> sucursales;
	private SucursalesMinDTO sucursal;

	public FilterGenericDTO() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Integer getUltimos() {
		return ultimos;
	}

	public void setUltimos(Integer ultimos) {
		this.ultimos = ultimos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public ProductoListDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoListDTO producto) {
		this.producto = producto;
	}

	public Integer getValido() {
		return valido;
	}

	public void setValido(Integer valido) {
		this.valido = valido;
	}

	public Integer getAlerta() {
		return alerta;
	}

	public void setAlerta(Integer alerta) {
		this.alerta = alerta;
	}

	public Set<Integer> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<Integer> sucursales) {
		this.sucursales = sucursales;
	}

	public SucursalesMinDTO getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalesMinDTO sucursal) {
		this.sucursal = sucursal;
	}

	public Boolean getVentas() {
		return ventas;
	}

	public void setVentas(Boolean ventas) {
		this.ventas = ventas;
	}

	public Boolean getAsociados() {
		return asociados;
	}

	public void setAsociados(Boolean asociados) {
		this.asociados = asociados;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
