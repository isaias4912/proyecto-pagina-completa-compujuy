package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recuperacion_pass")
public class RecuperacionPass implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer idUser;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "fecha_tomado")
    private Date fechaTomado;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "tomado")
    private Boolean tomado;

    public RecuperacionPass() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaTomado() {
        return fechaTomado;
    }

    public void setFechaTomado(Date fechaTomado) {
        this.fechaTomado = fechaTomado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getTomado() {
        return tomado;
    }

    public void setTomado(Boolean tomado) {
        this.tomado = tomado;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}
