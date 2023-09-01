package ar.com.jsuper.dao;

import ar.com.jsuper.domain.FormaPagos;
import java.util.List;

public interface FormasPagoDAO extends GenericDAO<FormaPagos, Integer> {

	public List<FormaPagos> getListAllActive();

	public List<FormaPagos> getListAllActiveForProveedor();

	public List<FormaPagos> getListAllActiveForcliente();

	public List<FormaPagos> getListAllActiveForFacElec();

	public List<FormaPagos> getListAllActiveForCtaCteCli();

	public List<FormaPagos> getListAllActiveForCtaCteProv();
}
