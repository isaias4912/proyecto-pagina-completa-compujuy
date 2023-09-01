package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.CuentasCorrientes;
import ar.com.jsuper.domain.MovimientosCtaCte;
import ar.com.jsuper.domain.PagoPagoCtaCte;
import ar.com.jsuper.domain.PagosCtaCte;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.domain.utils.PagarCtaCte;
import java.math.BigDecimal;
import java.util.List;

public interface CuentasCorrientesDAO extends GenericDAO<CuentasCorrientes, Integer> {

	CuentasCorrientes createCtaCte(Integer idCliente, CuentasCorrientes cuentaCorriente);

	List<PagosCtaCte> getPagosFromMtoCtaCte(Integer idMtoCtaCte);

	List<MovimientosCtaCte> getDetailPagosFromMtoCtaCteSale(MovimientosCtaCte mtoCtaCte);

	MovimientosCtaCte getMtoCtaCte(Integer idMtoCtaCte);

	List<MovimientosCtaCte> pagar(PagarCtaCte pagarCtaCte);

	BigDecimal getSaldoTotalFromCtaCte(Integer idCuentaCte);

	Cliente verifiedClienteAndCtaCte(Cliente cliente, BigDecimal monto);

	Pagination<MovimientosCtaCte> getMovimientosCtaCte(FilterMovCtaCte filterMovCtaCte, int page, int pageSize, String fieldOrder, boolean reverse);

	List<PagosCtaCte> getDetailPagosFromMtoCtaCteSaldo(MovimientosCtaCte movimientoCtaCte);

	MovimientosCtaCte getMtoFromPagoCtaCteSaldo(Integer idPagoCtaCteSaldo);

	PagosCtaCte getPagoFromMtoCtaCte(Integer idMtoCtaCte);

	List<PagoPagoCtaCte> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte);
}
