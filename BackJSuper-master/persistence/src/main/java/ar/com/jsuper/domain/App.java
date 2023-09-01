package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app")
public class App implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "key_app")
    private String keyid;
    @Column(name = "logo")
    private String logo;
    @Column(name = "alt_logo")
    private String altLogo;
    //datos de afip

    @Column(name = "afip_token")
    private String afipToken;
    @Column(name = "afip_sign")
    private String afipSign;
    @Column(name = "afip_token_test")
    private String afipTokenTest;
    @Column(name = "afip_sign_test")
    private String afipSignTest;
    @Column(name = "afip_fecha_init_token")
    private Date afipFechaInitToken;
    @Column(name = "afip_fecha_end_token")
    private Date afipFechaEndToken;
    @Column(name = "afip_fecha_init_token_test")
    private Date afipFechaInitTokenTest;
    @Column(name = "afip_fecha_end_token_test")
    private Date afipFechaEndTokenTest;
    @Column(name = "afip_priv_key")
    private String afipPrivKey;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "directory")
    private String directory;
    // end datos de afip
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuracion_id")
    private Configuracion configuracion;

    public App() {
    }

    public App(Integer id) {
        this.id = id;
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

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAltLogo() {
        return altLogo;
    }

    public void setAltLogo(String altLogo) {
        this.altLogo = altLogo;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public String getAfipToken() {
        return afipToken;
    }

    public void setAfipToken(String afipToken) {
        this.afipToken = afipToken;
    }

    public String getAfipSign() {
        return afipSign;
    }

    public void setAfipSign(String afipSign) {
        this.afipSign = afipSign;
    }

    public Date getAfipFechaInitToken() {
        return afipFechaInitToken;
    }

    public void setAfipFechaInitToken(Date afipFechaInitToken) {
        this.afipFechaInitToken = afipFechaInitToken;
    }

    public Date getAfipFechaEndToken() {
        return afipFechaEndToken;
    }

    public void setAfipFechaEndToken(Date afipFechaEndToken) {
        this.afipFechaEndToken = afipFechaEndToken;
    }

    public String getAfipPrivKey() {
        return afipPrivKey;
    }

    public void setAfipPrivKey(String afipPrivKey) {
        this.afipPrivKey = afipPrivKey;
    }

    public Date getAfipFechaInitTokenTest() {
        return afipFechaInitTokenTest;
    }

    public void setAfipFechaInitTokenTest(Date afipFechaInitTokenTest) {
        this.afipFechaInitTokenTest = afipFechaInitTokenTest;
    }

    public Date getAfipFechaEndTokenTest() {
        return afipFechaEndTokenTest;
    }

    public void setAfipFechaEndTokenTest(Date afipFechaEndTokenTest) {
        this.afipFechaEndTokenTest = afipFechaEndTokenTest;
    }

    public String getAfipTokenTest() {
        return afipTokenTest;
    }

    public void setAfipTokenTest(String afipTokenTest) {
        this.afipTokenTest = afipTokenTest;
    }

    public String getAfipSignTest() {
        return afipSignTest;
    }

    public void setAfipSignTest(String afipSignTest) {
        this.afipSignTest = afipSignTest;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
