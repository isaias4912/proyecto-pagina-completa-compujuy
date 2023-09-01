package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CuentasCorrientesProv;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.PagoPagoCtaCteProv;
import ar.com.jsuper.domain.PagosCtaCteProv;
import ar.com.jsuper.domain.utils.FilterMovCtaCteProv;
import ar.com.jsuper.domain.utils.PagarCtaCteProv;
import java.util.List;

public interface CuentasCorrientesProvDAO extends GenericDAO<CuentasCorrientesProv, Integer> {

	public CuentasCorrientesProv getCtaCteByProveedor(Integer idProveedor);

	public MovimientosCtaCteProv pagarCuentaCteProv(MovimientosCtaCteProv pagoCtaCteProv);

	Pagination<MovimientosCtaCteProv> getMovimientosCtaCte(FilterMovCtaCteProv filterMovCtaCte, int page, int pageSize, String fieldOrder, boolean reverse);

	public MovimientosCtaCteProv getMtoCtaCte(Integer idMtoCtaCte);

	public List<MovimientosCtaCteProv> pagar(PagarCtaCteProv pagarCtaCte);

	public MovimientosCtaCteProv getMtoFromPagoCtaCteSaldo(Integer idPagoCtaCteSaldo);

	public List<PagosCtaCteProv> getDetailPagosFromMtoCtaCteSaldo(MovimientosCtaCteProv movimientoCtaCte);

	public PagosCtaCteProv getPagoFromMtoCtaCte(Integer idMtoCtaCte);

	public List<PagoPagoCtaCteProv> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte);

}
