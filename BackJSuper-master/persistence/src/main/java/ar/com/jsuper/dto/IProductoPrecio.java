/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rafa
 */
public interface IProductoPrecio {

	public BigDecimal getPrecioVenta();

	public void setPrecioVenta(BigDecimal precioVenta);

	public List<ListaPreciosDTO> getPrecios();

	public void setPrecios(List<ListaPreciosDTO> precios);

	public BigDecimal getPrecioCosto();

	public void setPrecioCosto(BigDecimal precioCosto);
}
