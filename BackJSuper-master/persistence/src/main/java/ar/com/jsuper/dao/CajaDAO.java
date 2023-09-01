package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.DetalleTransaccionCaja;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.utils.DataTransaccionCaja;
import ar.com.jsuper.domain.utils.FilterCaja;
import ar.com.jsuper.domain.utils.FilterTransaccion;
import java.util.List;
import java.util.Set;

public interface CajaDAO extends GenericDAO<Caja, Integer> {

	public boolean isExistPC(String nombre);

	public boolean isExistCaja(String nombre);

	public Pagination<Caja> getCajasBypage(FilterCaja filterCaja, int page, int pageSize, String fieldOrder, boolean reverse);

	public int enabledOrdisabled(Set<Caja> cajas, boolean value);

	public Caja getByNombrePC(String nombrePC) throws BussinessException;

	public TransaccionCaja getLastTransaccionCaja(Caja caja) throws BussinessException;

	public TransaccionCaja getTransaccionCaja(Integer id);

	public DetalleTransaccionCaja getDetalleTransaccionCaja(Integer id);

	public TransaccionCaja abrirCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException;

	public TransaccionCaja cerrarCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException;

	public TransaccionCaja movimientoCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException;

	public TransaccionCaja anulaVenta(DataTransaccionCaja dataTransaccionCaja) throws BussinessException;

	public Pagination<TransaccionCaja> getTransaccionBypage(FilterTransaccion filterTransaccion, int page, int pageSize, String fieldOrder, boolean reverse);

	public List getAllDetalleTransacciones(Integer idTransaccion, Integer tipo);

	public Pagination<DetalleTransaccionCaja> getDetalleTransaccionBypage(FilterTransaccion filterTransaccion, int page, int pageSize, String fieldOrder, boolean reverse);

	public Caja getCajaFromTransaccion(Integer id);
}
