package ar.com.jsuper.services;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterCbte;
import ar.com.jsuper.domain.utils.PaginationRequestDTO;
import ar.com.jsuper.dto.CbtePresEncMinDTO;
import ar.com.jsuper.dto.CbtePresupuestoEncDTO;
import ar.com.jsuper.dto.CbtePresupuestoEncVenDTO;

import java.util.List;
import java.util.Map;

public interface PresupuestoService {
    Pagination<CbtePresEncMinDTO> getByPage(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO);

    CbtePresupuestoEncDTO insertPresupuesto(CbtePresupuestoEncDTO cbtePresupuestoEncDTO);

    CbtePresupuestoEncDTO get(Integer id);

    CbtePresupuestoEncVenDTO getToVen(Integer id);

    Boolean updateEstadoCbte(Integer id, Integer value);

    List<CbtePresEncMinDTO> getPresupuestos(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO);

    Map getDataPresupuestos();
}
