package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity implementation class for Entity: mov
 */
public class HistPreciosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal valorInicial;

    private BigDecimal valorFinal;
    private BigDecimal porcentaje;
    private BigDecimal diferencia;

    private String detalle;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date fecha;
    private TipoPreciosMinDTO tipoPrecio;
    private Integer idUser;
    private String userName;

    public HistPreciosDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TipoPreciosMinDTO getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(TipoPreciosMinDTO tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
