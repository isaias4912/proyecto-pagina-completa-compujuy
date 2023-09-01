/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author rafa22
 */
public class DataClientDesktop {

	List<SucursalesDTO> sucursales;
	List<OfertasProdDTO> ofertas;
	List<ParamGenericDTO> entradasCaja;
	List<ParamGenericDTO> salidasCaja;
	List<ListaPreciosDTO> listaPrecios;
	CajaDTO caja;
	ConfiguracionMinDTO configuracion;
	TransaccionCajaDTO transaccionCaja;
	String logo;
	String logoCbte;
	String nombreApp;
	Map dataVentas;

	public List<SucursalesDTO> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<SucursalesDTO> sucursales) {
		this.sucursales = sucursales;
	}

	public CajaDTO getCaja() {
		return caja;
	}

	public void setCaja(CajaDTO caja) {
		this.caja = caja;
	}

	public TransaccionCajaDTO getTransaccionCaja() {
		return transaccionCaja;
	}

	public void setTransaccionCaja(TransaccionCajaDTO transaccionCaja) {
		this.transaccionCaja = transaccionCaja;
	}

	public List<OfertasProdDTO> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<OfertasProdDTO> ofertas) {
		this.ofertas = ofertas;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNombreApp() {
		return nombreApp;
	}

	public void setNombreApp(String nombreApp) {
		this.nombreApp = nombreApp;
	}

	public List<ParamGenericDTO> getEntradasCaja() {
		return entradasCaja;
	}

	public void setEntradasCaja(List<ParamGenericDTO> entradasCaja) {
		this.entradasCaja = entradasCaja;
	}

	public List<ParamGenericDTO> getSalidasCaja() {
		return salidasCaja;
	}

	public void setSalidasCaja(List<ParamGenericDTO> salidasCaja) {
		this.salidasCaja = salidasCaja;
	}

	public ConfiguracionMinDTO getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(ConfiguracionMinDTO configuracion) {
		this.configuracion = configuracion;
	}

	public Map getDataVentas() {
		return dataVentas;
	}

	public void setDataVentas(Map dataVentas) {
		this.dataVentas = dataVentas;
	}

	public List<ListaPreciosDTO> getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(List<ListaPreciosDTO> listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

    public String getLogoCbte() {
        return logoCbte;
    }

    public void setLogoCbte(String logoCbte) {
        this.logoCbte = logoCbte;
    }

}
