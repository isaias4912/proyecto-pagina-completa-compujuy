package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.CajaDTO;
import ar.com.jsuper.dto.CajaListDTO;
import ar.com.jsuper.dto.DataSummaryVentasDTO;
import ar.com.jsuper.dto.DataTransaccionCajaDTO;
import ar.com.jsuper.dto.DetalleTransaccionCajaListDTO;
import ar.com.jsuper.dto.DetalleTransaccionCajaMinDTO;
import ar.com.jsuper.dto.FilterCajaDTO;
import ar.com.jsuper.dto.FilterTransaccionDTO;
import ar.com.jsuper.dto.TransaccionCajaDTO;
import ar.com.jsuper.dto.TransaccionCajaSinCajaDTO;
import ar.com.jsuper.dto.TransaccionListCajaDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CajasService {

	public Pagination<CajaListDTO> getCajasBypage(FilterCajaDTO cajaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

	public CajaListDTO getCajaFromTransaccion(Integer idTransaccion);

	public boolean isExistPC(String nombre);

	public boolean isExistCaja(String nombre);

	public CajaDTO saveCaja(CajaDTO cajaDTO) throws BussinessException;

	public CajaDTO updateCaja(Integer id, CajaDTO cajaDTO) throws BussinessException;

	public CajaDTO get(Integer id) throws BussinessException;

	public List<CajaListDTO> getAllActive() throws BussinessException;

	public void delete(CajaDTO cajaDTO) throws BussinessException;

	public void enabledOrdisabled(Set<CajaDTO> cajasDTO, boolean value) throws BussinessException;

	public TransaccionCajaDTO abrirCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException;

	public TransaccionCajaDTO cerrarrCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException;

	public TransaccionCajaDTO movimientoCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException;

	public TransaccionCajaDTO anulaVenta(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException;

	public DataSummaryVentasDTO getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException;

	public TransaccionCajaSinCajaDTO getTransaccionCaja(Integer idTransaccion);

	public DetalleTransaccionCajaMinDTO getDetalleTransaccionCaja(Integer idDetTransaccion);

	public Pagination<TransaccionListCajaDTO> getTransaccionBypage(FilterTransaccionDTO filterTransaccionDTO, int page, int pageSize, String fieldOrder, boolean reverse);

	public Pagination<DetalleTransaccionCajaListDTO> getDetalleTransaccionBypage(FilterTransaccionDTO filterTransaccionDTO, int page, int pageSize, String fieldOrder, boolean reverse);

	public List getAllDetalleTransacciones(Integer idTransaccion, Integer tipo);

	public Map getDataCaja();
}
