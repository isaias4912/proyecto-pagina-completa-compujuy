package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.CajaDAO;
import ar.com.jsuper.dao.SucursalDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.DetalleTransaccionCaja;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.utils.DataSummaryVentas;
import ar.com.jsuper.domain.utils.DataTransaccionCaja;
import ar.com.jsuper.domain.utils.FilterCaja;
import ar.com.jsuper.domain.utils.FilterTransaccion;
import ar.com.jsuper.dto.CajaDTO;
import ar.com.jsuper.dto.SucursalesDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.CajasService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CajasServiceImpl implements CajasService {

	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private CajaDAO cajaDAO;
	@Autowired
	private SucursalDAO sucursalDAO;
	@Autowired
	private VentasDAO ventasDAO;
	@Autowired
	private FacturaElectronicaService facturaElectronicaService;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public boolean isExistPC(String nombre) {
		return cajaDAO.isExistPC(nombre);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public boolean isExistCaja(String nombre) {
		return cajaDAO.isExistCaja(nombre);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<CajaListDTO> getCajasBypage(FilterCajaDTO cajaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		FilterCaja filterCaja = modelMapper.map(cajaFilter, FilterCaja.class);
		// armamos los filtros extras para los daos
		Pagination<Caja> paginacionCajas = cajaDAO.getCajasBypage(filterCaja, page, pageSize, fieldOrder, reverse);
		List<CajaListDTO> lista = new ArrayList<>();
		if (paginacionCajas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionCajas.getData(), CajaListDTO.class);
		}
		Pagination<CajaListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionCajas.getTotal());
		pag.setPageSize(paginacionCajas.getPageSize());
		pag.setPage(paginacionCajas.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public CajaListDTO getCajaFromTransaccion(Integer idTransaccion) {
		return modelMapper.map(cajaDAO.getCajaFromTransaccion(idTransaccion), CajaListDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public CajaDTO saveCaja(CajaDTO cajaDTO) throws BussinessException {
		Caja caja = modelMapper.map(cajaDTO, Caja.class);
		this.validateCaja(caja, null);
		caja.setActivo(Boolean.TRUE);
		if (Objects.isNull(caja.getConControlEstricto())) {
			caja.setConControlEstricto(false);
		}
		if (Objects.isNull(caja.getModificaAdicional())) {
			caja.setModificaAdicional(false);
		}
		if (Objects.isNull(caja.getModificaDescuento())) {
			caja.setModificaDescuento(false);
		}
		if (Objects.isNull(caja.getModificaPrecio())) {
			caja.setModificaPrecio(false);
		}
		cajaDAO.insert(caja);
		return modelMapper.map(caja, CajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public CajaDTO updateCaja(Integer id, CajaDTO cajaDTO) throws BussinessException {
		cajaDTO.setId(id);
		Caja caja = modelMapper.map(cajaDTO, Caja.class);
		Caja cajaBD = cajaDAO.get(id);
		this.validateCaja(caja, cajaBD);
		sucursalDAO.saveUpdateOrDelete(cajaBD, caja.getSucursales(), new HashSet<>(cajaBD.getSucursales()));
		cajaDAO.update(cajaBD, caja);
		return modelMapper.map(caja, CajaDTO.class);
	}
	
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Map getDataCaja() {
		return facturaElectronicaService.getPuntoVentas();
	}

	private void validateCaja(Caja caja, Caja cajaBD) {
		if (Objects.isNull(cajaBD)) {
			// comprobamos  que no exista una pc con ese nombre  ya   con ese nombre
			if (this.isExistPC(caja.getNombreMaquina())) {
				throw new HttpMessageNotWritableException("Ya existe la maquina con el nombre :" + caja.getNombreMaquina());
			}
			if (this.isExistCaja(caja.getNombre())) {
				throw new HttpMessageNotWritableException("Ya existe una caja con el nombre :" + caja.getNombre());
			}
		} else {
			if (!caja.getNombreMaquina().trim().equals(cajaBD.getNombreMaquina())) {
				if (this.isExistPC(caja.getNombreMaquina())) {
					throw new HttpMessageNotWritableException("Ya existe la maquina con el nombre :" + caja.getNombreMaquina());
				}
			}
			if (!caja.getNombre().trim().equals(cajaBD.getNombre())) {
				if (this.isExistCaja(caja.getNombre())) {
					throw new HttpMessageNotWritableException("Ya existe una caja con el nombre :" + caja.getNombre());
				}
			}
		}
		Set<Sucursales> sucursales = caja.getSucursales();
		for (Sucursales sucursal : sucursales) {
			Boolean res = sucursalDAO.isSucursalOfApp(sucursal.getId());
			if (!res) {
				throw new DataIntegrityViolationException("La sucursal seleccionada no esta habilitada, por favor inicie sesión nuevamente, o refresque el sistema.");
			}
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public CajaDTO get(Integer id) throws BussinessException {
		Caja caja = cajaDAO.get(id);
		return modelMapper.map(caja, CajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void delete(CajaDTO cajaDTO) throws BussinessException {
		Caja caja = modelMapper.map(cajaDTO, Caja.class);
		cajaDAO.delete(caja);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public List<CajaListDTO> getAllActive() throws BussinessException {
		List<Caja> lista = cajaDAO.getAllListActive();
		return modelMapper.mapAsList(lista, CajaListDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabled(Set<CajaDTO> cajasDTO, boolean value) throws BussinessException {
		Set<Caja> cajas = modelMapper.mapAsSet(cajasDTO, Caja.class);
		cajaDAO.enabledOrdisabled(cajas, value);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public TransaccionCajaDTO abrirCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		DataTransaccionCaja dataTransaccionCaja = modelMapper.map(dataTransaccionCajaDTO, DataTransaccionCaja.class);
		TransaccionCaja transaccionCaja = cajaDAO.abrirCaja(dataTransaccionCaja);
		return modelMapper.map(transaccionCaja, TransaccionCajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public TransaccionCajaDTO cerrarrCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		DataTransaccionCaja dataTransaccionCaja = modelMapper.map(dataTransaccionCajaDTO, DataTransaccionCaja.class);
		TransaccionCaja transaccionCaja = cajaDAO.cerrarCaja(dataTransaccionCaja);
		return modelMapper.map(transaccionCaja, TransaccionCajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public TransaccionCajaDTO movimientoCaja(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		DataTransaccionCaja dataTransaccionCaja = modelMapper.map(dataTransaccionCajaDTO, DataTransaccionCaja.class);
		TransaccionCaja transaccionCaja = cajaDAO.movimientoCaja(dataTransaccionCaja);
		return modelMapper.map(transaccionCaja, TransaccionCajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public TransaccionCajaDTO anulaVenta(DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		DataTransaccionCaja dataTransaccionCaja = modelMapper.map(dataTransaccionCajaDTO, DataTransaccionCaja.class);
		TransaccionCaja transaccionCaja = cajaDAO.anulaVenta(dataTransaccionCaja);
		return modelMapper.map(transaccionCaja, TransaccionCajaDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public DataSummaryVentasDTO getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException {
		DataSummaryVentas dataSummaryVentas = ventasDAO.getSummaryVentasFromTransaccion(idTransaccion);
		TransaccionCaja transaccionCaja = cajaDAO.getTransaccionCaja(idTransaccion);
		// calculamos el total de efectuvo que deberia haber
		BigDecimal totalDineroEfectivo = BigDecimal.ZERO;
		for (DetalleTransaccionCaja detalleTransaccion : transaccionCaja.getDetallesTransaccionCaja()) {
			if (!Objects.isNull(detalleTransaccion.getMonto())) {
				if (detalleTransaccion.getTipo() == 3 || detalleTransaccion.getTipo() == 4) {
					totalDineroEfectivo = totalDineroEfectivo.subtract(detalleTransaccion.getMonto());
				} else {
					totalDineroEfectivo = totalDineroEfectivo.add(detalleTransaccion.getMonto());
				}
			}
		}
		totalDineroEfectivo = totalDineroEfectivo.add(dataSummaryVentas.getTotalEfectivo());
		dataSummaryVentas.setTotalDineroEfectivo(totalDineroEfectivo);
		dataSummaryVentas.setTransaccionCaja(transaccionCaja);
		return modelMapper.map(dataSummaryVentas, DataSummaryVentasDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public TransaccionCajaSinCajaDTO getTransaccionCaja(Integer idTransaccion) {
		return modelMapper.map(cajaDAO.getTransaccionCaja(idTransaccion), TransaccionCajaSinCajaDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public DetalleTransaccionCajaMinDTO getDetalleTransaccionCaja(Integer idDetTransaccion) {
		return modelMapper.map(cajaDAO.getDetalleTransaccionCaja(idDetTransaccion), DetalleTransaccionCajaMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<TransaccionListCajaDTO> getTransaccionBypage(FilterTransaccionDTO filterTransaccionDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterTransaccion filterTransaccion = modelMapper.map(filterTransaccionDTO, FilterTransaccion.class);
		// armamos los filtros extras para los daos
		Pagination<TransaccionCaja> paginacionTransaccion = cajaDAO.getTransaccionBypage(filterTransaccion, page, pageSize, fieldOrder, reverse);
		List<TransaccionListCajaDTO> lista = new ArrayList<>();
		if (paginacionTransaccion.getData() != null) {
			lista = modelMapper.mapAsList(paginacionTransaccion.getData(), TransaccionListCajaDTO.class);
		}
		Pagination<TransaccionListCajaDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionTransaccion.getTotal());
		pag.setPageSize(paginacionTransaccion.getPageSize());
		pag.setPage(paginacionTransaccion.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<DetalleTransaccionCajaListDTO> getDetalleTransaccionBypage(FilterTransaccionDTO filterTransaccionDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterTransaccion filterTransaccion = modelMapper.map(filterTransaccionDTO, FilterTransaccion.class);
		// armamos los filtros extras para los daos
		Pagination<DetalleTransaccionCaja> paginacionTransaccion = cajaDAO.getDetalleTransaccionBypage(filterTransaccion, page, pageSize, fieldOrder, reverse);
		List<DetalleTransaccionCajaListDTO> lista = new ArrayList<>();
		if (paginacionTransaccion.getData() != null) {
			lista = modelMapper.mapAsList(paginacionTransaccion.getData(), DetalleTransaccionCajaListDTO.class);
		}
		Pagination<DetalleTransaccionCajaListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionTransaccion.getTotal());
		pag.setPageSize(paginacionTransaccion.getPageSize());
		pag.setPage(paginacionTransaccion.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List getAllDetalleTransacciones(Integer idTransaccion, Integer tipo) {
		List<DetalleTransaccionCaja> detalleTransaccionCaja = cajaDAO.getAllDetalleTransacciones(idTransaccion, tipo);
		return modelMapper.mapAsList(detalleTransaccionCaja, DetalleTransaccionCajaListDTO.class);
	}
}
