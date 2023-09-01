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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo_menu")
    private Integer tipoMenu;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id")
    private Roles rol;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "menu_menu_item", joinColumns = {
        @JoinColumn(name = "menu_id", nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "menu_item_id",
                        nullable = false, updatable = false, insertable = false)})
    private Set<MenuItem> menuItems;
//    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
//    private Set<MenuItem> menuItems;

    public Menu() {
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

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Integer getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(Integer tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

}
