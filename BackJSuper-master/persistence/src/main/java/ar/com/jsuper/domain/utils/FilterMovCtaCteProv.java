/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Proveedores;

/**
 *
 * @author rafael
 */
public class FilterMovCtaCteProv {

    private Proveedores proveedor;
    private Integer estado;
    private Integer idFacturaCompra;

	public Proveedores getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}



    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(Integer idFacturaVenta) {
        this.idFacturaCompra = idFacturaVenta;
    }

}
