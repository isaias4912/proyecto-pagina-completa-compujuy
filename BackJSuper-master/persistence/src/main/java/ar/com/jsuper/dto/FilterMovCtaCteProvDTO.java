/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;


/**
 *
 * @author rafael
 */
public class FilterMovCtaCteProvDTO {

    private ProveedoresMinDTO proveedor;
    private Integer estado;
    private Integer idFacturaCompra;

	public ProveedoresMinDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedoresMinDTO proveedor) {
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
