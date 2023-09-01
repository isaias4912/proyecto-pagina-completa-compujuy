package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpresasDTO implements Serializable {

    private Integer id;
    private String razonSocial;
    private String cuit;
    private TipoEmpresa tipoEmpresa;
    private Boolean estado;
    private String observacion;
    private Set<DomiciliosDTO> domicilios;
    private Set<ContactosDTO> contactos;
    private Set<TelefonosDTO> telefonos;
    private PersonasNanoDTO persona;
    public EmpresasDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Set<DomiciliosDTO> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(Set<DomiciliosDTO> domicilios) {
        this.domicilios = domicilios;
    }

    public Set<ContactosDTO> getContactos() {
        return contactos;
    }

    public void setContactos(Set<ContactosDTO> contactos) {
        this.contactos = contactos;
    }

    public Set<TelefonosDTO> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Set<TelefonosDTO> telefonos) {
        this.telefonos = telefonos;
    }

    public PersonasNanoDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonasNanoDTO persona) {
        this.persona = persona;
    }

}
