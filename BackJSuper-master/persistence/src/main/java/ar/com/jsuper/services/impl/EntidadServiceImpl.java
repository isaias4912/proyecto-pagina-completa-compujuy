package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ClientesDAO;
import ar.com.jsuper.dao.CuentasCorrientesDAO;
import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.ClienteMinSinEntDTO;
import ar.com.jsuper.dto.CtaCteMinDTO;
import ar.com.jsuper.dto.EmailDTO;
import ar.com.jsuper.dto.EntidadMinDTO;
import ar.com.jsuper.dto.EntidadDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.EntidadService;
import ar.com.jsuper.utils.TipoEntidad;
import ar.com.jsuper.utils.UtilEntidad;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntidadServiceImpl implements EntidadService {

	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private EntidadDAO entidadDAO;
	@Autowired
	private ClientesDAO clientesDAO;
	@Autowired
	private CuentasCorrientesDAO cuentasCorrientesDAO;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<EntidadMinDTO> getEntidadesBypage(FilterEntidad filterEntidad, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		filterEntidad.setTipo(TipoEntidad.TODAS.theState);
		// armamos los filtros extras para los daos
		Pagination<Entidad> paginacionEmpresas = entidadDAO.getEntidadesBypage(filterEntidad, page, pageSize, fieldOrder, reverse);
		List<EntidadMinDTO> lista = new ArrayList<>();
		if (paginacionEmpresas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionEmpresas.getData(), EntidadMinDTO.class);
		}

		Pagination<EntidadMinDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionEmpresas.getTotal());
		pag.setPageSize(paginacionEmpresas.getPageSize());
		pag.setPage(paginacionEmpresas.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination<EntidadMinDTO> getEntidadesByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Pagination<Entidad> paginacionPersonas = entidadDAO.getEntidadesByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
		List<EntidadMinDTO> lista = new ArrayList<>();
		if (paginacionPersonas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionPersonas.getData(), EntidadMinDTO.class);
		}
		Pagination<EntidadMinDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionPersonas.getTotal());
		pag.setPageSize(paginacionPersonas.getPageSize());
		pag.setPage(paginacionPersonas.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination<EntidadDTO> getEntidadesByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Pagination<Entidad> paginacionPersonas = entidadDAO.getEntidadesByMultipleFilterFull(query, page, pageSize, fieldOrder, reverse);
		List<EntidadDTO> lista = new ArrayList<>();
		if (paginacionPersonas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionPersonas.getData(), EntidadDTO.class);
		}
		Pagination<EntidadDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionPersonas.getTotal());
		pag.setPageSize(paginacionPersonas.getPageSize());
		pag.setPage(paginacionPersonas.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmailDTO> getEntidadesAndEmail(String query) {
		List<Entidad> entidades = entidadDAO.getEntidadesAndEmail(query);
		List<EmailDTO> emails = new ArrayList<>();
		if (Objects.nonNull(entidades) && entidades.size() > 0) {
			for (Entidad entidad : entidades) {
				if (Objects.nonNull(entidad.getContactos()) & entidad.getContactos().size() > 0) {
					for (Contactos contacto : entidad.getContactos()) {
						EmailDTO emailDTO = new EmailDTO();
						emailDTO.setId(contacto.getId().toString());
						emailDTO.setNombre(UtilEntidad.getDescripcion(entidad));
						emailDTO.setEmail(contacto.getDescripcion());
						emailDTO.setDescripcion(contacto.getDetalle());
						emailDTO.setTipoEntidad(entidad.getTipo());
						emails.add(emailDTO);
					}
				}
			}
		}
		return emails;
	}

	/**
	 * Devuelve un listado de todos los clientes, sin paginar, sin telefonos, direccion ni contactos
	 *
	 * @param query
	 * @param page
	 * @param pageSize
	 * @param fieldOrder
	 * @param reverse
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EntidadMinDTO> getAllEntidadesAndClienteByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Pagination<EntidadMinDTO> pagination = this.getEntidadesByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
		List<EntidadMinDTO> lista = pagination.getData();
		lista.stream().forEach(ent -> {
			Cliente cliente = this.clientesDAO.getClienteMinByEntidad(ent.getId());
			if (Objects.nonNull(cliente)) {
				ClienteMinSinEntDTO clienteMinSinEntDTO = this.modelMapper.map(cliente, ClienteMinSinEntDTO.class);
				CtaCteMinDTO cc = clienteMinSinEntDTO.getCuentaCorriente();
				if (Objects.nonNull(cc)) {
					BigDecimal saldoTotal = cuentasCorrientesDAO.getSaldoTotalFromCtaCte(cc.getId());
					cc.setSaldo(saldoTotal);
					cc.setMargen(cc.getLimite().subtract(saldoTotal));
				}
				ent.setCliente(clienteMinSinEntDTO);
			} else {
				ent.setCliente(null);
			}
		});
		return pagination.getData();
	}

	/**
	 * Devuelve un listado de todos los clientes, sin paginar, con telefonos, direccion ni contactos
	 *
	 * @param query
	 * @param page
	 * @param pageSize
	 * @param fieldOrder
	 * @param reverse
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EntidadDTO> getAllEntidadesAndClienteByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Pagination<EntidadDTO> pagination = this.getEntidadesByMultipleFilterFull(query, page, pageSize, fieldOrder, reverse);
		List<EntidadDTO> lista = pagination.getData();
		lista.stream().forEach(ent -> {
			Cliente cliente = this.clientesDAO.getClienteMinByEntidad(ent.getId());
			if (Objects.nonNull(cliente)) {
				ClienteMinSinEntDTO clienteMinSinEntDTO = this.modelMapper.map(cliente, ClienteMinSinEntDTO.class);
				CtaCteMinDTO cc = clienteMinSinEntDTO.getCuentaCorriente();
				if (Objects.nonNull(cc)) {
					BigDecimal saldoTotal = cuentasCorrientesDAO.getSaldoTotalFromCtaCte(cc.getId());
					cc.setSaldo(saldoTotal);
					cc.setMargen(cc.getLimite().subtract(saldoTotal));
				}
				ent.setCliente(clienteMinSinEntDTO);
			} else {
				ent.setCliente(null);
			}
		});
		return pagination.getData();
	}

}
