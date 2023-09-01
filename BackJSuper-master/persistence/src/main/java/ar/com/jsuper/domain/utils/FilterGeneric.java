package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.Sucursales;
import java.util.Set;

public class FilterGeneric {

	private String text;
	private String nombre;
	private Integer id;
	private Integer alerta;
	private String fechaInicial;
	private String fechaFinal;
	private Integer ultimos;
	private Integer activo;
	private Integer valido;
	private Producto producto;
	private Boolean ventas;
	private Boolean asociados;
	private Set<Integer> sucursales;
	private Sucursales sucursal;

	public FilterGeneric() {
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getAlerta() {
		return alerta;
	}

	public void setAlerta(Integer alerta) {
		this.alerta = alerta;
	}

	public Integer getValido() {
		return valido;
	}

	public void setValido(Integer valido) {
		this.valido = valido;
	}

	public Set<Integer> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<Integer> sucursales) {
		this.sucursales = sucursales;
	}

	public Sucursales getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursales sucursal) {
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
