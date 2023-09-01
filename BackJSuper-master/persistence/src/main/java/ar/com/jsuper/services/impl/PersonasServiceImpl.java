package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ClientesDAO;
import ar.com.jsuper.dao.ContactosDAO;
import ar.com.jsuper.dao.CuentasCorrientesDAO;
import ar.com.jsuper.dao.DomiciliosDAO;
import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.dao.PersonasDAO;
import ar.com.jsuper.dao.TelefonosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.PersonasDTO;
import ar.com.jsuper.dto.PersonasListDTO;
import ar.com.jsuper.dto.PersonasMinDTO;
import ar.com.jsuper.dto.PersonasSinClienteDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.PersonasService;
import ar.com.jsuper.utils.TipoEntidad;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonasServiceImpl implements PersonasService {

	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private PersonasDAO personasDAO;
	@Autowired
	private EntidadDAO entidadDAO;
	@Autowired
	private ClientesDAO clientesDAO;
	@Autowired
	private DomiciliosDAO domiciliosDAO;
	@Autowired
	private TelefonosDAO telefonosPersonaDAO;
	@Autowired
	private ContactosDAO contactosPersonaDAO;
	@Autowired
	private CuentasCorrientesDAO cuentasCorrientesDAO;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<PersonasListDTO> getPersonasBypage(FilterEntidad filterPersona, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		filterPersona.setTipo(TipoEntidad.PERSONA.theState);
		// armamos los filtros extras para los daos
		Pagination<Entidad> paginacionPersonas = entidadDAO.getEntidadesBypage(filterPersona, page, pageSize, fieldOrder, reverse);
		List<PersonasListDTO> lista = new ArrayList<>();
		if (paginacionPersonas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionPersonas.getData(), PersonasListDTO.class);
		}

		Pagination<PersonasListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionPersonas.getTotal());
		pag.setPageSize(paginacionPersonas.getPageSize());
		pag.setPage(paginacionPersonas.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination<PersonasMinDTO> getPersonasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Pagination<Entidad> paginacionPersonas = entidadDAO.getEntidadesPerByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
		List<PersonasMinDTO> lista = new ArrayList<>();
		if (paginacionPersonas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionPersonas.getData(), PersonasMinDTO.class);
		}
		Pagination<PersonasMinDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionPersonas.getTotal());
		pag.setPageSize(paginacionPersonas.getPageSize());
		pag.setPage(paginacionPersonas.getPage());
		return pag;
	}

	/**
	 * no devuelve pagainado
	 *
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<PersonasMinDTO> getAllPersAndCliByMultipleFilter(String query, String fieldOrder, boolean reverse, boolean saldo) {
		Pagination<PersonasMinDTO> pagination = this.getPersonasByMultipleFilter(query, 0, 0, fieldOrder, reverse);
		List<PersonasMinDTO> listaPersonas = pagination.getData();
//        if (saldo) {
//            for (PersonasMinDTO persona : listaPersonas) {
//                if (!Objects.isNull(persona.getCliente())) {
//                    if (!Objects.isNull(persona.getCliente().getCuentaCorriente())) {
//                        CtaCteMinDTO cc = persona.getCliente().getCuentaCorriente();
//                        BigDecimal saldoTotal = cuentasCorrientesDAO.getSaldoTotalFromCtaCte(cc.getId());
//                        cc.setSaldo(saldoTotal);
//                        cc.setMargen(cc.getLimite().subtract(saldoTotal));
//                    }
//                }
//            }
//        }
		return listaPersonas;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public PersonasMinDTO getByDni(String dni) {
		Entidad persona = entidadDAO.getByDni(dni);
		return modelMapper.map(persona, PersonasMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public PersonasDTO insertPersona(PersonasDTO personasDTO) throws BussinessException {
		Entidad persona = modelMapper.map(personasDTO, Entidad.class);
		this.entidadDAO.insertPersona(persona);
		domiciliosDAO.saveUpdateOrDelete(persona, persona.getDomicilios(), new HashSet<>());
		contactosPersonaDAO.saveUpdateOrDelete(persona, persona.getContactos(), new HashSet<>());
		telefonosPersonaDAO.saveUpdateOrDelete(persona, persona.getTelefonos(), new HashSet<>());
		return modelMapper.map(persona, PersonasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public PersonasDTO updatePersona(Integer id, PersonasDTO personaDTO) throws BussinessException {
		personaDTO.setId(id);
		Entidad persona = modelMapper.map(personaDTO, Entidad.class);
		Entidad personaBD = entidadDAO.get(id);
		domiciliosDAO.saveUpdateOrDelete(persona, persona.getDomicilios(), personaBD.getDomicilios());
		contactosPersonaDAO.saveUpdateOrDelete(persona, persona.getContactos(), personaBD.getContactos());
		telefonosPersonaDAO.saveUpdateOrDelete(persona, persona.getTelefonos(), personaBD.getTelefonos());
		entidadDAO.updatePersona(personaBD, persona);
		// verificamos que el dni no se haya modificado, en caso de que si modificamos el del cliente o proveedor tambien para estar sincronizados
		
		return modelMapper.map(persona, PersonasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public PersonasSinClienteDTO get(Integer id) throws BussinessException {
		Entidad persona = entidadDAO.get(id);
		return modelMapper.map(persona, PersonasSinClienteDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void delete(Integer id) throws BussinessException {
		Entidad persona = entidadDAO.get(id);
		entidadDAO.delete(persona);
	}

}
