package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "familias")
public class Familias implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nivel")
    private int nivel;
    @Column(name = "nombreCorto")
    private String nombreCorto;
    @ManyToOne
    @JoinColumn(name = "familias_id")
    private Familias familia;
    @ManyToOne
    @JoinColumn(name = "app_id")
    private App app;
    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL)
    private Set<Familias> familias;

    public Familias() {
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

    public Familias getFamilia() {
        return familia;
    }

    public void setFamilia(Familias familia) {
        this.familia = familia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Set<Familias> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Familias> familias) {
        this.familias = familias;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

//    public String getKeyid() {
//        return keyid;
//    }
//
//    public void setKeyid(String keyid) {
//        this.keyid = keyid;
//    }

   
}
