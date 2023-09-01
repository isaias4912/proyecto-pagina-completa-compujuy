package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CbteEncPresupuesto;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.utils.*;
import ar.com.jsuper.utils.EstadoCbte;

public interface PresupuestoDAO extends GenericDAO<CbteEncPresupuesto, Integer> {
    Pagination<CbteEncPresupuesto> getPresupuestoByPage(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO);

    CbteEncPresupuesto updateEstadoCbte(CbteEncPresupuesto cbteEncPresupuesto, EstadoCbte estadoCbte);

    CbteEncPresupuesto insertPresupuesto(CbteEncPresupuesto cbteEncPresupuesto) throws BussinessException;
}
