package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ProveedoresDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterProveedores;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ar.com.jsuper.dao.CuentasCorrientesProvDAO;
import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.utils.FilterMovCtaCteProv;
import ar.com.jsuper.domain.utils.PagarCtaCteProv;
import ar.com.jsuper.dto.FilterMovCtaCteProvDTO;
import ar.com.jsuper.dto.InfoCtaCteProvDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvMinDTO;
import ar.com.jsuper.dto.PagarCtaCteProvDTO;
import ar.com.jsuper.dto.PagoPagoCtaCteDTO;
import ar.com.jsuper.dto.PagosCtaCteProvDTO;
import ar.com.jsuper.dto.ProveedoresDTO;
import ar.com.jsuper.dto.ProveedoresMinDTO;
import ar.com.jsuper.dto.ProveedoresListDTO;
import ar.com.jsuper.services.ProveedoresService;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.TipoEntidad;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

	@Autowired
	private ProveedoresDAO proveedorDAO;
	@Autowired
	private CuentasCorrientesProvDAO cuentasCorrientesProvDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private EntidadDAO entidadDAO;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<ProveedoresListDTO> getProveedoresBypage(FilterProveedores filterProveedores, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		// armamos los filtros extras para los daos
		Pagination<Proveedores> paginacionProveedores = proveedorDAO.getProveedoresBypage(filterProveedores, page, pageSize, fieldOrder, reverse);
		List<ProveedoresListDTO> lista = new ArrayList<>();
		if (paginacionProveedores.getData() != null) {
			lista = modelMapper.mapAsList(paginacionProveedores.getData(), ProveedoresListDTO.class);
		}
		Pagination<ProveedoresListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionProveedores.getTotal());
		pag.setPageSize(paginacionProveedores.getPageSize());
		pag.setPage(paginacionProveedores.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<ProveedoresListDTO> getProveedoresByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		Pagination<Proveedores> paginacionProveedores = proveedorDAO.getProveedoresByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
		List<ProveedoresListDTO> lista = new ArrayList<>();
		if (paginacionProveedores.getData() != null) {
			lista = modelMapper.mapAsList(paginacionProveedores.getData(), ProveedoresListDTO.class);
		}
		Pagination<ProveedoresListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionProveedores.getTotal());
		pag.setPageSize(paginacionProveedores.getPageSize());
		pag.setPage(paginacionProveedores.getPage());
		return pag;
	}

	private void validateProveedor(Proveedores proveedor, Entidad entidad) {
		if (Objects.nonNull(proveedor.getEntidad()) && Objects.nonNull(proveedor.getEntidad().getId())) {
			Boolean isProveedor = this.entidadDAO.isProveedor(proveedor.getEntidad().getId());

			if (isProveedor) {
				if (entidad.getTipo().equals(TipoEntidad.PERSONA)) {
					throw new DataIntegrityViolationException("La persona " + entidad.getPersona().getNombreFinal() + ", con DNI " + entidad.getPersona().getDni() + " ya es proveedor");
				}
				if (entidad.getTipo().equals(TipoEntidad.EMPRESA)) {
					throw new DataIntegrityViolationException("La empresa " + entidad.getEmpresa().getRazonSocial() + ", con CUIT " + entidad.getEmpresa().getCuit() + " ya es proveedor");
				}
			} else {
				//validamos que tipo de entidad es  y que tipo de doc nesecita el cliente segun el tipo de entidad
				if (entidad.getTipo().equals(TipoEntidad.EMPRESA)) {
					if (!proveedor.getTipoDocProveedor().equals(Doc.CUIT)) {
						throw new DataIntegrityViolationException("Error - EL tipo de documento para una empresa debe ser un CUIT");
					}
				}
			}
		} else {
			throw new DataIntegrityViolationException("Error - El proveedor debe estar asociado a una persona/empresa");
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ProveedoresDTO insertProveedor(ProveedoresDTO proveedoresDTO) throws BussinessException {
		Proveedores proveedor = modelMapper.map(proveedoresDTO, Proveedores.class);
		Entidad entidad = this.entidadDAO.get(proveedor.getEntidad().getId());
		this.validateProveedor(proveedor, entidad);
		proveedor = proveedorDAO.insert(proveedor);
		this.entidadDAO.setDoc(entidad, proveedor);
		return modelMapper.map(proveedor, ProveedoresDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ProveedoresDTO updateProveedor(Integer id, ProveedoresDTO proveedorDTO) throws BussinessException {
		proveedorDTO.setId(id);
		Proveedores proveedor = modelMapper.map(proveedorDTO, Proveedores.class);
		Proveedores proveedorBD = proveedorDAO.get(id);
		proveedorDAO.updateProveedor(proveedorBD, proveedor);
		return modelMapper.map(proveedor, ProveedoresDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabled(Set<ProveedoresDTO> proveedorsDTO, boolean value) throws BussinessException {
		for (ProveedoresDTO proveedorDTO : proveedorsDTO) {
			Proveedores proveedor = proveedorDAO.get(proveedorDTO.getId());
			proveedor.setEstado(value);
			proveedorDAO.update(proveedor);
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ProveedoresDTO get(Integer id) {
		Proveedores proveedor = proveedorDAO.getByApp(id);
		return modelMapper.map(proveedor, ProveedoresDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void delete(Integer id) throws BussinessException {
		Proveedores proveedor = proveedorDAO.get(id);
		proveedorDAO.delete(proveedor);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<ProveedoresMinDTO> getByProveedorMatch(String q) {
		List<Proveedores> proveedores = proveedorDAO.getByProveedorMatch(q);
		return modelMapper.mapAsList(proveedores, ProveedoresMinDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public ProveedoresMinDTO getProveedorMin(Integer id) {
		return modelMapper.map(proveedorDAO.getProveedorMin(id), ProveedoresMinDTO.class);
	}

	@Override
	@Transactional()
	public MovimientosCtaCteProvDTO pagarCuentaCteProv(MovimientosCtaCteProvDTO movimientosCtaCteProvDTO) {
		MovimientosCtaCteProv movimientosCtaCteProv = modelMapper.map(movimientosCtaCteProvDTO, MovimientosCtaCteProv.class);
		movimientosCtaCteProv = cuentasCorrientesProvDAO.pagarCuentaCteProv(movimientosCtaCteProv);
		return modelMapper.map(movimientosCtaCteProv, MovimientosCtaCteProvDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public PaginationData<MovimientosCtaCteProvDTO, InfoCtaCteProvDTO> getMovimientosCtaCte(FilterMovCtaCteProvDTO filter, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterMovCtaCteProv filterMtoDTO = modelMapper.map(filter, FilterMovCtaCteProv.class);
		// armamos los filtros extras para los daos
		Pagination<MovimientosCtaCteProv> paginacionMtoCtaCte = cuentasCorrientesProvDAO.getMovimientosCtaCte(filterMtoDTO, page, pageSize, fieldOrder, reverse);
		List<MovimientosCtaCteProvDTO> lista = new ArrayList<>();
		if (paginacionMtoCtaCte.getData() != null) {
			lista = modelMapper.mapAsList(paginacionMtoCtaCte.getData(), MovimientosCtaCteProvDTO.class);
		}
		InfoCtaCteProvDTO infoCtaCteDTO = this.getInfoCtaCteFromMtos(lista, filter);
		PaginationData<MovimientosCtaCteProvDTO, InfoCtaCteProvDTO> pag = new PaginationData<>();
		pag.setData(lista);
		pag.setDataAditional(infoCtaCteDTO);
		pag.setTotal(paginacionMtoCtaCte.getTotal());
		pag.setPageSize(paginacionMtoCtaCte.getPageSize());
		pag.setPage(paginacionMtoCtaCte.getPage());
		return pag;
	}

	private InfoCtaCteProvDTO getInfoCtaCteFromMtos(List<MovimientosCtaCteProvDTO> movimientos, FilterMovCtaCteProvDTO filter) {
		BigDecimal saldo = movimientos.stream().filter(x -> x.getSaldo() != null).map(x -> x.getSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add);
		InfoCtaCteProvDTO infoCtaCteDTO = new InfoCtaCteProvDTO();
		infoCtaCteDTO.setSaldo(saldo);
		if (!Objects.isNull(filter.getProveedor()) && !Objects.isNull(filter.getProveedor().getId())) {
			ProveedoresMinDTO prov = this.getProveedorMin(filter.getProveedor().getId());
			infoCtaCteDTO.setProveedor(prov);
		}
		return infoCtaCteDTO;
	}

	/**
	 * pagar cta cte de un proveedor x
	 *
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public List<MovimientosCtaCteProvDTO> pagar(PagarCtaCteProvDTO pagarCtaCteProvDTO) {
		PagarCtaCteProv pagarCtaCte = modelMapper.map(pagarCtaCteProvDTO, PagarCtaCteProv.class);
		return modelMapper.mapAsList(cuentasCorrientesProvDAO.pagar(pagarCtaCte), MovimientosCtaCteProvDTO.class);
	}

	/**
	 * mandamos un preview de como quedara la cuenta segun lo que se selecciono
	 *
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public PagarCtaCteProvDTO pagarPreview(PagarCtaCteProvDTO pagarCtaCteProvDTO) {
		List<MovimientosCtaCteProvDTO> movimientosCtaCteDTO = pagarCtaCteProvDTO.getMovimientosCtaCte();
		List<MovimientosCtaCteProvDTO> movimientosCtaCteDTOTemp = new ArrayList<>();
		BigDecimal paga = pagarCtaCteProvDTO.getPagoCon();
		BigDecimal totalPaga = BigDecimal.ZERO;
		for (MovimientosCtaCteProvDTO movimientoCtaCteDTO : movimientosCtaCteDTO) {
			MovimientosCtaCteProv movimientoCtaCteDB = cuentasCorrientesProvDAO.getMtoCtaCte(movimientoCtaCteDTO.getId());
			MovimientosCtaCteProvDTO m = new MovimientosCtaCteProvDTO();
			m.setId(movimientoCtaCteDB.getId());
			m.setMontoTotal(movimientoCtaCteDB.getMontoTotal());
			m.setMonto(movimientoCtaCteDB.getMontoTotal());
			m.setPaga(movimientoCtaCteDB.getSaldo());
			m.setFecha(movimientoCtaCteDB.getFecha());
			m.setSaldo(BigDecimal.ZERO);
			m.setSaldoActual(movimientoCtaCteDB.getSaldo());
			m.setTipo(movimientoCtaCteDB.getTipo());
			m.setPagado(true);
			m.setPagadoConfirm(false);
			movimientosCtaCteDTOTemp.add(m);
//			totalPaga = totalPaga.add(m.getPaga());
			totalPaga = totalPaga.add(m.getSaldoActual());
			paga = paga.subtract(movimientoCtaCteDB.getSaldo(), MathContext.DECIMAL128);
		}
		pagarCtaCteProvDTO.setTotalPaga(totalPaga);
		pagarCtaCteProvDTO.setMontoNeto(totalPaga);
		// sacamos el interes si corresponde
		BigDecimal interesCargado = pagarCtaCteProvDTO.getInteres().abs(MathContext.DECIMAL128);
		if (pagarCtaCteProvDTO.getInteres().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal interes = (interesCargado.multiply(totalPaga, MathContext.DECIMAL128)).divide(new BigDecimal(100), MathContext.DECIMAL128);
			if (pagarCtaCteProvDTO.getInteres().compareTo(BigDecimal.ZERO) > 0) {
				pagarCtaCteProvDTO.setMontoTotal(totalPaga.add(interes, MathContext.DECIMAL128));
			} else {
				pagarCtaCteProvDTO.setMontoTotal(totalPaga.subtract(interes, MathContext.DECIMAL128));
			}
		} else {
			pagarCtaCteProvDTO.setMontoTotal(totalPaga);
		}
		pagarCtaCteProvDTO.setMovimientosCtaCte(movimientosCtaCteDTOTemp);
		pagarCtaCteProvDTO.setPagoCon(pagarCtaCteProvDTO.getMontoTotal());
		return pagarCtaCteProvDTO;
	}

	/**
	 * Devuelve todos los detalles de un mto, incluye saldos pendientes
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<PagosCtaCteProvDTO> getDetailPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		MovimientosCtaCteProv movimientoCtaCte = this.cuentasCorrientesProvDAO.getMtoCtaCte(idMtoCtaCte);
		List<PagosCtaCteProvDTO> lista = null;
		lista = modelMapper.mapAsList(cuentasCorrientesProvDAO.getDetailPagosFromMtoCtaCteSaldo(movimientoCtaCte), PagosCtaCteProvDTO.class);
		if (Objects.isNull(lista) || lista.isEmpty()) {
			if (!Objects.isNull(movimientoCtaCte.getPagoCtaCte())) {
				PagosCtaCteProvDTO ctaCteDTO = modelMapper.map(movimientoCtaCte.getPagoCtaCte(), PagosCtaCteProvDTO.class);
				MovimientosCtaCteProvMinDTO m = new MovimientosCtaCteProvMinDTO();
				m.setMonto(BigDecimal.ZERO);
				m.setMontoTotal(BigDecimal.ZERO);
				m.setPagado(Boolean.TRUE);
				m.setSaldo(BigDecimal.ZERO);
				m.setSaldoActual(BigDecimal.ZERO);
				ctaCteDTO.setMovimiento(m);
				lista.add(ctaCteDTO);
			}
		} else {
			lista.stream().forEach(item -> {
				MovimientosCtaCteProvMinDTO m = modelMapper.map(this.cuentasCorrientesProvDAO.getMtoFromPagoCtaCteSaldo(item.getId()), MovimientosCtaCteProvMinDTO.class);
				item.setMovimiento(m);
			});
			if (lista.get(0).getMovimiento().getPagado()) {
				PagosCtaCteProvDTO ctaCteDTO = modelMapper.map(this.cuentasCorrientesProvDAO.getPagoFromMtoCtaCte(lista.get(0).getMovimiento().getId()), PagosCtaCteProvDTO.class);
				MovimientosCtaCteProvMinDTO m = new MovimientosCtaCteProvMinDTO();
				m.setMonto(BigDecimal.ZERO);
				m.setMontoTotal(BigDecimal.ZERO);
				m.setPagado(Boolean.TRUE);
				m.setSaldo(BigDecimal.ZERO);
				m.setSaldoActual(BigDecimal.ZERO);
				ctaCteDTO.setMovimiento(m);
				lista.add(0, ctaCteDTO);
			}
		}
		return lista;
	}

	/**
	 * Devuelve todos los pagos de un pago de cta cte de un movimiento de cta cte
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<PagoPagoCtaCteDTO> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte) {
		return modelMapper.mapAsList(cuentasCorrientesProvDAO.getPagosFromIdPagoCtaCte(idPagoCtaCte), PagoPagoCtaCteDTO.class);
	}
}
