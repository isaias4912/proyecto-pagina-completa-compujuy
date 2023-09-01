package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;

public class DetMovimientosMinDTO implements Serializable {

	private Integer id;
	private BigDecimal cantidad;
	private String referencia;
	private String nombreProducto;
	private Integer idProducto;
	private Integer tipo;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal valorInicial;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal valorFinal;
	private EncMovimientosMinDTO factura;
	private ProductoMinDTO producto;

	public DetMovimientosMinDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
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

	public EncMovimientosMinDTO getFactura() {
		return factura;
	}

	public void setFactura(EncMovimientosMinDTO factura) {
		this.factura = factura;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public ProductoMinDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoMinDTO producto) {
		this.producto = producto;
	}

}
