package ar.com.jsuper.dto;

import java.io.Serializable;
import java.util.List;

public class MenuItemDTO implements Serializable {

	private int id;
	private String nombre;
	private String descripcion;
	private Integer orden;
	private Integer tipoHref;
	private String href;
	private String classCss;
	private String title;
	private String routerLink;
	private String icon2;
	private List<MenuItemDTO> menuItems;

	public MenuItemDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getTipoHref() {
		return tipoHref;
	}

	public void setTipoHref(Integer tipoHref) {
		this.tipoHref = tipoHref;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getClassCss() {
		return classCss;
	}

	public void setClassCss(String classCss) {
		this.classCss = classCss;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MenuItemDTO> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemDTO> menuItems) {
		this.menuItems = menuItems;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	public String getIcon2() {
		return icon2;
	}

	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

}
