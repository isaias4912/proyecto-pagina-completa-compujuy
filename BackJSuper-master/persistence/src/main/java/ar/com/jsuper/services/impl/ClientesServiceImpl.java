package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ClientesDAO;
import ar.com.jsuper.dao.ContactosDAO;
import ar.com.jsuper.dao.PersonasDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.utils.ExistClienteOrPersona;
import ar.com.jsuper.domain.utils.FilterClientes;
import ar.com.jsuper.dto.ClienteDTO;
import ar.com.jsuper.dto.ExistClienteOrPersonaDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ClientesService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;
import ar.com.jsuper.dao.CuentasCorrientesDAO;
import ar.com.jsuper.dao.DomiciliosDAO;
import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.dao.TelefonosDAO;
import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.domain.CuentasCorrientes;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.MovimientosCtaCte;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.domain.utils.PagarCtaCte;
import ar.com.jsuper.dto.ClienteListDTO;
import ar.com.jsuper.dto.ClienteMapDTO;
import ar.com.jsuper.dto.ClienteMicDTO;
import ar.com.jsuper.dto.ClienteMinDTO;
import ar.com.jsuper.dto.ClienteMinSinEntDTO;
import ar.com.jsuper.dto.ContactosDTO;
import ar.com.jsuper.dto.CtaCteMinDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.MovimientosCtaCteDTO;
import ar.com.jsuper.dto.MovimientosCtaCteMinDTO;
import ar.com.jsuper.dto.MovimientosCtaCteNanoDTO;
import ar.com.jsuper.dto.PagarCtaCteDTO;
import ar.com.jsuper.dto.PagosCtaCteDTO;
import ar.com.jsuper.dto.InfoCtaCteDTO;
import ar.com.jsuper.dto.PagoPagoCtaCteDTO;
import ar.com.jsuper.dto.PersonasDTO;
import ar.com.jsuper.services.PersonasService;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEntidad;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import org.apache.log4j.Logger;

@Service
public class ClientesServiceImpl implements ClientesService {

	@Autowired
	private ClientesDAO clientesDAO;
	@Autowired
	private PersonasDAO personasDAO;
	@Autowired
	private EntidadDAO entidadDAO;
	@Autowired
	private DomiciliosDAO domiciliosDAO;
	@Autowired
	private TelefonosDAO telefonosPersonaDAO;
	@Autowired
	private ContactosDAO contactosPersonaDAO;
	@Autowired
	private CuentasCorrientesDAO cuentasCorrientesDAO;

	@Autowired
	private OrikaBeanMapper modelMapper;
	private Logger logger = Logger.getLogger(ClientesServiceImpl.class);

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<ClienteListDTO> getClientesBypage(FilterClientes filterCliente, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		// armamos los filtros extras para los daos 
		Pagination<Cliente> paginacionClientes = clientesDAO.getClientesBypage(filterCliente, page, pageSize, fieldOrder, reverse);
		List<ClienteListDTO> lista = new ArrayList<>();
		if (paginacionClientes.getData() != null) {
			lista = modelMapper.mapAsList(paginacionClientes.getData(), ClienteListDTO.class);
		}
		this.setCtaCte(lista);
		Pagination<ClienteListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionClientes.getTotal());
		pag.setPageSize(paginacionClientes.getPageSize());
		pag.setPage(paginacionClientes.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<ClienteListDTO> getClientesByMultipleFilter(String query, Integer activo, Integer page, Integer pageSize, String fieldOrder, Boolean reverse) {
		Pagination<Cliente> paginacionPersonas = clientesDAO.getClientesByMultipleFilter(query, activo, page, pageSize, fieldOrder, reverse);
		List<ClienteListDTO> lista = new ArrayList<>();
		if (paginacionPersonas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionPersonas.getData(), ClienteListDTO.class);
		}
		this.setCtaCte(lista);
		Pagination<ClienteListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionPersonas.getTotal());
		pag.setPageSize(paginacionPersonas.getPageSize());
		pag.setPage(paginacionPersonas.getPage());
		return pag;
	}

	private void setCtaCte(List<ClienteListDTO> lista) {
		lista.forEach(cli -> {
			CtaCteMinDTO cc = cli.getCuentaCorriente();
			if (Objects.nonNull(cc)) {
				BigDecimal saldoTotal = cuentasCorrientesDAO.getSaldoTotalFromCtaCte(cc.getId());
				cc.setSaldo(saldoTotal);
				cc.setMargen(cc.getLimite().subtract(saldoTotal));
			}
		});
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<ClienteMinDTO> getClientesByNameOrDni(String query, Integer activo) {
		List lista = clientesDAO.getClientesByNameOrDni(query, activo);
		return modelMapper.mapAsList(lista, ClienteMapDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ClienteMinDTO getClienteMinById(Integer id) {
		Cliente cliente = clientesDAO.getClienteMinById(id);
		return modelMapper.map(cliente, ClienteMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ClienteMinSinEntDTO getClienteMinByEntidad(Integer id) {
		Cliente cliente = clientesDAO.getClienteMinByEntidad(id);
		return modelMapper.map(cliente, ClienteMinSinEntDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ClienteMicDTO getClienteMicById(Integer id) {
		Cliente cliente = clientesDAO.getClienteMicById(id);
		return modelMapper.map(cliente, ClienteMicDTO.class);
	}

	private void validateCliente(Cliente cliente, Entidad entidad) {
		if (Objects.nonNull(cliente.getEntidad()) && Objects.nonNull(cliente.getEntidad().getId())) {
			Boolean isCliente = this.entidadDAO.isCliente(cliente.getEntidad().getId());

			if (isCliente) {
				if (entidad.getTipo().equals(TipoEntidad.PERSONA)) {
					throw new DataIntegrityViolationException("La persona " + entidad.getPersona().getNombreFinal() + ", con DNI " + entidad.getPersona().getDni() + " ya es cliente");
				}
				if (entidad.getTipo().equals(TipoEntidad.EMPRESA)) {
					throw new DataIntegrityViolationException("La empresa " + entidad.getEmpresa().getRazonSocial() + ", con CUIT " + entidad.getEmpresa().getCuit() + " ya es cliente");
				}
			}
			this.validateEntidad(cliente);

		} else {
			throw new DataIntegrityViolationException("Error - El cliente debe estar asociado a una persona/empresa");
		}
	}

	private void validateEntidad(Cliente cliente) {
		//validamos que tipo de entidad es  y que tipo de doc nesecita el cliente segun el tipo de entidad
		if (Objects.isNull(cliente.getEntidad())) {
			throw new DataIntegrityViolationException("Error - El cliente debe estar asociado a una persona/empresa");
		}
		if (cliente.getEntidad().getTipo().equals(TipoEntidad.EMPRESA)) {
			if (Objects.isNull(cliente.getEntidad().getEmpresa())) {
				throw new DataIntegrityViolationException("Error - El cliente debe estar asociado a una persona");
			}
			if (Objects.isNull(cliente.getEntidad().getEmpresa().getRazonSocial()) || cliente.getEntidad().getEmpresa().getRazonSocial().trim().equals("")) {
				throw new DataIntegrityViolationException("Error - El cliente debe tener una razon social");
			}
			if (!cliente.getTipoDocCliente().equals(Doc.CUIT)) {
				throw new DataIntegrityViolationException("Error - El tipo de documento para una empresa debe ser un CUIT");
			}
		}
		if (cliente.getEntidad().getTipo().equals(TipoEntidad.PERSONA)) {
			if (Objects.isNull(cliente.getEntidad().getPersona())) {
				throw new DataIntegrityViolationException("Error - El cliente debe estar asociado a una persona");
			}
			if (Objects.isNull(cliente.getEntidad().getPersona().getApellido()) || cliente.getEntidad().getPersona().getApellido().trim().equals("")) {
				throw new DataIntegrityViolationException("Error - El cliente debe tener un apellido");
			}
			if (Objects.isNull(cliente.getEntidad().getPersona().getNombre()) || cliente.getEntidad().getPersona().getNombre().trim().equals("")) {
				throw new DataIntegrityViolationException("Error - El cliente debe tener un nombre");
			}
			if (Objects.isNull(cliente.getEntidad().getPersona().getDni()) || cliente.getEntidad().getPersona().getDni().trim().equals("")) {
				throw new DataIntegrityViolationException("Error - El cliente debe tener un dni");
			}
		}
	}

	/**
	 * INserta un cliente, la entidad puede ser una existente o una nueva Aqui no se modifica el documento porque son nuevos o no se deberia modificar
	 *
	 * @param clienteDTO
	 * @return
	 * @throws BussinessException
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ClienteDTO insertClienteFromEntidad(ClienteDTO clienteDTO) throws BussinessException {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		Entidad entidadDB = this.entidadDAO.get(clienteDTO.getEntidad().getId());
		Entidad entidadCliente = cliente.getEntidad();
		if (Objects.isNull(entidadDB)) {
			this.validateEntidad(cliente);
			if (entidadCliente.getTipo().equals(TipoEntidad.PERSONA)) {
				this.entidadDAO.insertPersona(entidadCliente);
			}
			if (entidadCliente.getTipo().equals(TipoEntidad.EMPRESA)) {
				entidadCliente.getEmpresa().setTipoEmpresa(TipoEmpresa.value(cliente.getTipoCliente().theState));
				this.entidadDAO.insertEmpresa(entidadCliente);
			}
			domiciliosDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getDomicilios(), new HashSet<>());
			contactosPersonaDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getContactos(), new HashSet<>());
			telefonosPersonaDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getTelefonos(), new HashSet<>());
			cliente.setEntidad(entidadCliente);
		} else {
			this.validateCliente(cliente, entidadDB);
			if (entidadCliente.getTipo().equals(TipoEntidad.PERSONA)) {
				entidadDAO.updatePersona(entidadDB, entidadCliente);
			}
			if (entidadCliente.getTipo().equals(TipoEntidad.EMPRESA)) {
				entidadDAO.updateEmpresa(entidadDB, entidadCliente);
			}
			domiciliosDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getDomicilios(), entidadDB.getDomicilios());
			contactosPersonaDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getContactos(), entidadDB.getContactos());
			telefonosPersonaDAO.saveUpdateOrDelete(entidadCliente, entidadCliente.getTelefonos(), entidadDB.getTelefonos());
		}
		cliente = clientesDAO.insert(cliente);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ClienteDTO insertCliente(ClienteDTO clienteDTO) throws BussinessException {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		Entidad entidad = this.entidadDAO.get(clienteDTO.getEntidad().getId());
		this.validateCliente(cliente, entidad);
		cliente = clientesDAO.insert(cliente);
		this.entidadDAO.setDoc(entidad, cliente);
		return modelMapper.map(cliente, ClienteDTO.class);
//		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ClienteDTO updateCliente(Integer id, ClienteDTO clienteDTO) throws BussinessException {
		clienteDTO.setId(id);
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		Cliente clienteBD = clientesDAO.get(id);
		cliente = clientesDAO.update(clienteBD, cliente);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ClienteDTO get(Integer id) throws BussinessException {
		Cliente cliente = clientesDAO.getByApp(id);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ExistClienteOrPersonaDTO getIsExistClienteOrPersona(String dni) throws BussinessException {
		Cliente cliente = clientesDAO.getByDni(dni);
		ExistClienteOrPersona existClienteOrPersona = new ExistClienteOrPersona();
		if (Objects.isNull(cliente)) {
			existClienteOrPersona.setIsExistCliente(Boolean.FALSE);
			// verficamos si existe como persona
			Personas persona = personasDAO.getByDni(dni);
			if (Objects.isNull(persona)) {
				existClienteOrPersona.setIsExistPersona(Boolean.FALSE);
			} else {
				existClienteOrPersona.setIsExistPersona(Boolean.TRUE);
				existClienteOrPersona.setPersonas(persona);
			}
		} else {
			existClienteOrPersona.setIsExistCliente(Boolean.TRUE);
			existClienteOrPersona.setCliente(cliente);
		}
		ExistClienteOrPersonaDTO existClienteOrPersonaDTO = modelMapper.map(existClienteOrPersona, ExistClienteOrPersonaDTO.class);
		return existClienteOrPersonaDTO;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public List getListByDniOrName(FilterGenericDTO filter) {
		FilterGeneric filterGeneric = modelMapper.map(filter, FilterGeneric.class);
		List lista = clientesDAO.getListByDniOrName(filterGeneric);
		return modelMapper.mapAsList(lista, ClienteListDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabled(Set<ClienteDTO> clientesDTO, boolean value) throws BussinessException {
		for (ClienteDTO clienteDTO : clientesDTO) {
			Cliente cliente = clientesDAO.get(clienteDTO.getId());
			cliente.setEstado(value);
			clientesDAO.update(cliente);
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void delete(Integer id) throws BussinessException {
		Cliente cliente = clientesDAO.get(id);
		clientesDAO.delete(cliente);
	}

	/**
	 * Devuelve un cliente por DNI
	 *
	 * @param dni
	 * @return
	 * @throws BussinessException
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ClienteDTO getByDni(String dni) throws BussinessException {
		Cliente c = clientesDAO.getOnlyClienteByDni(dni);
		return modelMapper.map(c, ClienteDTO.class);
	}

	/**
	 * Devuelve los mails de un cliente
	 *
	 * @param idCliente
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<ContactosDTO> getMails(Integer idCliente) {
		Set<ContactosDTO> contactosDTO = modelMapper.mapAsSet(clientesDAO.getContactos(idCliente, 1), ContactosDTO.class);
		return contactosDTO;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public CtaCteMinDTO createCtaCte(Integer id, CtaCteMinDTO ctaCteSinCliente) {
		CuentasCorrientes cuentaCorriente = modelMapper.map(ctaCteSinCliente, CuentasCorrientes.class);
		cuentaCorriente = cuentasCorrientesDAO.createCtaCte(id, cuentaCorriente);
		return modelMapper.map(cuentaCorriente, CtaCteMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public CtaCteMinDTO updateCtaCte(Integer id, CtaCteMinDTO cajaDTO) {
		cajaDTO.setId(id);
		CuentasCorrientes cc = modelMapper.map(cajaDTO, CuentasCorrientes.class);
		CuentasCorrientes ccBD = cuentasCorrientesDAO.get(id);
		cuentasCorrientesDAO.update(ccBD, cc);
		return modelMapper.map(cc, CtaCteMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public CtaCteMinDTO getCtaCte(Integer id) {
		CuentasCorrientes ccBD = cuentasCorrientesDAO.get(id);
		if (Objects.isNull(ccBD)) {
			return null;
		} else {
			CtaCteMinDTO ccscdto = modelMapper.map(ccBD, CtaCteMinDTO.class);
			ccscdto.setSaldo(cuentasCorrientesDAO.getSaldoTotalFromCtaCte(ccscdto.getId()));
			ccscdto.setMargen(ccscdto.getLimite().subtract(ccscdto.getSaldo(), MathContext.DECIMAL128));
			return ccscdto;
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> getMovimientosCtaCte(FilterMovCtaCte filterMtoDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		// armamos los filtros extras para los daos
		Pagination<MovimientosCtaCte> paginacionMtoCtaCte = cuentasCorrientesDAO.getMovimientosCtaCte(filterMtoDTO, page, pageSize, fieldOrder, reverse);
		List<MovimientosCtaCteDTO> lista = new ArrayList<>();
		if (paginacionMtoCtaCte.getData() != null) {
			lista = modelMapper.mapAsList(paginacionMtoCtaCte.getData(), MovimientosCtaCteDTO.class);
		}
		InfoCtaCteDTO infoCtaCteDTO = this.getInfoCtaCteFromMtos(lista, filterMtoDTO);
		PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> pag = new PaginationData<>();
		pag.setData(lista);
		pag.setDataAditional(infoCtaCteDTO);
		pag.setTotal(paginacionMtoCtaCte.getTotal());
		pag.setPageSize(paginacionMtoCtaCte.getPageSize());
		pag.setPage(paginacionMtoCtaCte.getPage());
		return pag;
	}

	private InfoCtaCteDTO getInfoCtaCteFromMtos(List<MovimientosCtaCteDTO> movimientos, FilterMovCtaCte filter) {
		BigDecimal saldo = movimientos.stream().filter(x -> x.getSaldo() != null).map(x -> x.getSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add);
		InfoCtaCteDTO infoCtaCteDTO = new InfoCtaCteDTO();
		infoCtaCteDTO.setSaldo(saldo);
		if (Objects.nonNull(filter.getCliente()) && Objects.nonNull(filter.getCliente().getId())) {
			ClienteMinDTO cli = this.getClienteMinById(filter.getCliente().getId());
			infoCtaCteDTO.setCliente(cli);
			infoCtaCteDTO.setActivoCliente(cli.getEstado());
			if (Objects.nonNull(cli.getCuentaCorriente())) {
//				CtaCteMinDTO ctaCte = this.getCtaCte(cli.getIdCuentaCorriente());
				infoCtaCteDTO.setLimite(cli.getCuentaCorriente().getLimite());
				infoCtaCteDTO.setActivoCtaCte(cli.getCuentaCorriente().getActivo());
				infoCtaCteDTO.setMargen(cli.getCuentaCorriente().getLimite().subtract(saldo, MathContext.DECIMAL128));
			}
		}
		return infoCtaCteDTO;
	}

	/**
	 * pagar cta cte de un cliente x
	 *
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public List<MovimientosCtaCteDTO> pagar(PagarCtaCteDTO pagarCtaCteDTO) {
		PagarCtaCte pagarCtaCte = modelMapper.map(pagarCtaCteDTO, PagarCtaCte.class);
		return modelMapper.mapAsList(cuentasCorrientesDAO.pagar(pagarCtaCte), MovimientosCtaCteDTO.class);
	}

	/**
	 * mandamos un preview de como quedara la cuenta segun lo que se selecciono
	 *
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public PagarCtaCteDTO pagarPreview(PagarCtaCteDTO pagarCtaCteDTO) {
		List<MovimientosCtaCteDTO> movimientosCtaCteDTO = pagarCtaCteDTO.getMovimientosCtaCte();
		List<MovimientosCtaCteDTO> movimientosCtaCteDTOTemp = new ArrayList<>();
		BigDecimal paga = pagarCtaCteDTO.getPagoCon();
		BigDecimal totalPaga = BigDecimal.ZERO;
		for (MovimientosCtaCteDTO movimientoCtaCteDTO : movimientosCtaCteDTO) {
			MovimientosCtaCte movimientoCtaCteDB = cuentasCorrientesDAO.getMtoCtaCte(movimientoCtaCteDTO.getId());
			MovimientosCtaCteDTO m = new MovimientosCtaCteDTO();
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
		pagarCtaCteDTO.setTotalPaga(totalPaga);
		pagarCtaCteDTO.setMontoNeto(totalPaga);
		// sacamos el interes si corresponde
		BigDecimal interesCargado = pagarCtaCteDTO.getInteres().abs(MathContext.DECIMAL128);
		if (pagarCtaCteDTO.getInteres().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal interes = (interesCargado.multiply(totalPaga, MathContext.DECIMAL128)).divide(new BigDecimal(100), MathContext.DECIMAL128);
			if (pagarCtaCteDTO.getInteres().compareTo(BigDecimal.ZERO) > 0) {
				pagarCtaCteDTO.setMontoTotal(totalPaga.add(interes, MathContext.DECIMAL128));
			} else {
				pagarCtaCteDTO.setMontoTotal(totalPaga.subtract(interes, MathContext.DECIMAL128));
			}
		} else {
			pagarCtaCteDTO.setMontoTotal(totalPaga);
		}
		pagarCtaCteDTO.setMovimientosCtaCte(movimientosCtaCteDTOTemp);
		pagarCtaCteDTO.setPagoCon(pagarCtaCteDTO.getMontoTotal());
		return pagarCtaCteDTO;
	}
//        PagarCtaCte pagarCtaCte = modelMapper.map(pagarCtaCteDTO, PagarCtaCte.class);
//        return modelMapper.mapAsList(cuentasCorrientesDAO.pagar(pagarCtaCte), MovimientosCtaCteDTO.class);
//    }

	/**
	 * Devuelve todos los pagos de cta cte de un movimiento de cta cte
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<PagosCtaCteDTO> getPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		return modelMapper.mapAsList(cuentasCorrientesDAO.getPagosFromMtoCtaCte(idMtoCtaCte), PagosCtaCteDTO.class);
	}

	/**
	 * Devuelve todos los detalles de un mto, incluye saldos pendientes
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<PagosCtaCteDTO> getDetailPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		MovimientosCtaCte movimientoCtaCte = this.cuentasCorrientesDAO.getMtoCtaCte(idMtoCtaCte);
		List<PagosCtaCteDTO> lista = null;
		lista = modelMapper.mapAsList(cuentasCorrientesDAO.getDetailPagosFromMtoCtaCteSaldo(movimientoCtaCte), PagosCtaCteDTO.class);
		if (Objects.isNull(lista) || lista.isEmpty()) {
			if (!Objects.isNull(movimientoCtaCte.getPagoCtaCte())) {
				PagosCtaCteDTO ctaCteDTO = modelMapper.map(movimientoCtaCte.getPagoCtaCte(), PagosCtaCteDTO.class);
				MovimientosCtaCteMinDTO m = new MovimientosCtaCteMinDTO();
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
				MovimientosCtaCteMinDTO m = modelMapper.map(this.cuentasCorrientesDAO.getMtoFromPagoCtaCteSaldo(item.getId()), MovimientosCtaCteMinDTO.class);
				item.setMovimiento(m);
			});
			if (lista.get(0).getMovimiento().getPagado()) {
				PagosCtaCteDTO ctaCteDTO = modelMapper.map(this.cuentasCorrientesDAO.getPagoFromMtoCtaCte(lista.get(0).getMovimiento().getId()), PagosCtaCteDTO.class);
				MovimientosCtaCteMinDTO m = new MovimientosCtaCteMinDTO();
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

	private void prepareView(MovimientosCtaCte mto, List<MovimientosCtaCteDTO> lista) {
		if (mto.getTipo() == 4) {
			if (!Objects.isNull(lista) && lista.size() > 0) {
				lista.get(0).setId(null);
				lista.get(0).setTipo(null);
				lista.get(0).setMonto(lista.get(0).getPagoCtaCte().getMontoNeto());
				lista.get(0).setMontoTotal(lista.get(0).getPagoCtaCte().getMontoTotal());
				lista.get(0).setInteres(lista.get(0).getPagoCtaCte().getInteres());
				lista.get(0).setFecha(null);
				List<MovimientosCtaCteNanoDTO> listaMov = lista.get(0).getPagoCtaCte().getMovimientos();

			}
//			for (MovimientosCtaCteDTO m : lista) {
//				
//			}
		}
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
		return modelMapper.mapAsList(cuentasCorrientesDAO.getPagosFromIdPagoCtaCte(idPagoCtaCte), PagoPagoCtaCteDTO.class);
	}

}
