package ar.com.jsuper.dto;

public class AppDTO {

    private String nombre;
    private String descripcion;
    private SucursalesDTO sucursalPrincipal;
    private String logo;
    private String altLogo;
    private String pais;
    private Integer tipoEmpresa;

    public AppDTO() {
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

    public SucursalesDTO getSucursalPrincipal() {
        return sucursalPrincipal;
    }

    public void setSucursalPrincipal(SucursalesDTO sucursalPrincipal) {
        this.sucursalPrincipal = sucursalPrincipal;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(Integer tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

}
