package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "menu_item")
public class MenuItem implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "orden")
    private Integer orden;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY)
    private Set<MenuItem> menuItems;
    @Column(name = "tipo_href")
    private Integer tipoHref;
    @Column(name = "href")
    private String href;
    @Column(name = "class_css")
    private String classCss;
    @Column(name = "title")
    private String title;
    @Column(name = "router_link")
    private String routerLink;
    @Column(name = "icon2")
    private String icon2;

    public MenuItem() {
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

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getClassCss() {
        return classCss;
    }

    public void setClassCss(String classCss) {
        this.classCss = classCss;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
