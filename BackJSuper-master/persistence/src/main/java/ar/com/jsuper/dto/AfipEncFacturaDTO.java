package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Concepto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Entity implementation class for Entity: mov
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AfipEncFacturaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private AfipClienteDTO cliente;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCarga;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaFactura;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaServDesde;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaServHasta;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVenPag;
    private Comprobante comprobante;
    private Concepto concepto;
    private SimpleObjectDTO sucursal;
    private String numero;

    private Boolean activo;

    private Integer tipo;

    private Integer cargada;

    private Double subtotal;

    private Double total;

    private Double totalDescuentos;

    private Double totalAdicionales;

    private Double totalImpuestos;
    private Integer puntoVenta;
    private Double totalTributos;
    private Double totalBaseImp;
    private Double totalIVAs;
    private Boolean pagada;

    private Integer tipoPago;
    private Integer idSucursal;
    private SingleObjectDTO tipoCliente;
    private ClienteNanoDTO clienteApp;

    private Set<AfipDetFacturaDTO> items;
    private Set<AfipTributoDTO> tributos;
    private Set<AfipIVADTO> ivas;
    private Set<PagoVentasSinEncabDTO> pagoVentas;

    public AfipEncFacturaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AfipClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(AfipClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Date getFechaServDesde() {
        return fechaServDesde;
    }

    public void setFechaServDesde(Date fechaServDesde) {
        this.fechaServDesde = fechaServDesde;
    }

    public Date getFechaServHasta() {
        return fechaServHasta;
    }

    public void setFechaServHasta(Date fechaServHasta) {
        this.fechaServHasta = fechaServHasta;
    }

    public Date getFechaVenPag() {
        return fechaVenPag;
    }

    public void setFechaVenPag(Date fechaVenPag) {
        this.fechaVenPag = fechaVenPag;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getCargada() {
        return cargada;
    }

    public void setCargada(Integer cargada) {
        this.cargada = cargada;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getTotalAdicionales() {
        return totalAdicionales;
    }

    public void setTotalAdicionales(Double totalAdicionales) {
        this.totalAdicionales = totalAdicionales;
    }

    public Double getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(Double totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public Integer getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Integer tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Set<AfipDetFacturaDTO> getItems() {
        return items;
    }

    public void setItems(Set<AfipDetFacturaDTO> items) {
        this.items = items;
    }

    public Set<AfipTributoDTO> getTributos() {
        return tributos;
    }

    public void setTributos(Set<AfipTributoDTO> tributos) {
        this.tributos = tributos;
    }

    public Double getTotalTributos() {
        return totalTributos;
    }

    public void setTotalTributos(Double totalTributos) {
        this.totalTributos = totalTributos;
    }

    public Double getTotalIVAs() {
        return totalIVAs;
    }

    public void setTotalIVAs(Double totalIVAs) {
        this.totalIVAs = totalIVAs;
    }

    public Double getTotalBaseImp() {
        return totalBaseImp;
    }

    public void setTotalBaseImp(Double totalBaseImp) {
        this.totalBaseImp = totalBaseImp;
    }

    public Set<AfipIVADTO> getIvas() {
        return ivas;
    }

    public void setIvas(Set<AfipIVADTO> ivas) {
        this.ivas = ivas;
    }

    public ClienteNanoDTO getClienteApp() {
        return clienteApp;
    }

    public void setClienteApp(ClienteNanoDTO clienteApp) {
        this.clienteApp = clienteApp;
    }

    public Set<PagoVentasSinEncabDTO> getPagoVentas() {
        return pagoVentas;
    }

    public void setPagoVentas(Set<PagoVentasSinEncabDTO> pagoVentas) {
        this.pagoVentas = pagoVentas;
    }

    public SimpleObjectDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SimpleObjectDTO sucursal) {
        this.sucursal = sucursal;
    }

    public SingleObjectDTO getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(SingleObjectDTO tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Integer getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(Integer puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

}
