package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.EmpresasDAO;
import ar.com.jsuper.dao.DomiciliosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import java.util.ArrayList;
import java.util.List;
import ar.com.jsuper.dao.TelefonosDAO;
import ar.com.jsuper.dao.ContactosDAO;
import ar.com.jsuper.dao.CuentasCorrientesProvDAO;
import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.EmpresasDTO;
import ar.com.jsuper.dto.EmpresasMinDTO;
import ar.com.jsuper.dto.EmpresasListDTO;
import ar.com.jsuper.services.EmpresasService;
import ar.com.jsuper.utils.TipoEntidad;
import java.util.HashSet;

@Service
public class EmpresasServiceImpl implements EmpresasService {

	@Autowired
	private EmpresasDAO empresasDAO;
	@Autowired
	private EmpresasDAO empresaDAO;
	@Autowired
	private DomiciliosDAO domiciliosDAO;
	@Autowired
	private TelefonosDAO telefonosPersonaDAO;
	@Autowired
	private ContactosDAO contactosPersonaDAO;
	@Autowired
	private CuentasCorrientesProvDAO cuentasCorrientesProvDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private EntidadDAO entidadDAO;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<EmpresasListDTO> getEmpresasBypage(FilterEntidad empresaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		FilterEntidad filterEmpresa = modelMapper.map(empresaFilter, FilterEntidad.class);
		filterEmpresa.setTipo(TipoEntidad.EMPRESA.theState);
		// armamos los filtros extras para los daos
		Pagination<Entidad> paginacionEmpresas = entidadDAO.getEntidadesBypage(filterEmpresa, page, pageSize, fieldOrder, reverse);
		List<EmpresasListDTO> lista = new ArrayList<>();
		if (paginacionEmpresas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionEmpresas.getData(), EmpresasListDTO.class);
		}

		Pagination<EmpresasListDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionEmpresas.getTotal());
		pag.setPageSize(paginacionEmpresas.getPageSize());
		pag.setPage(paginacionEmpresas.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<EmpresasDTO> getEmpresasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		Pagination<Empresas> paginacionEmpresas = empresasDAO.getEmpresasByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
		List<EmpresasDTO> lista = new ArrayList<>();
		if (paginacionEmpresas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionEmpresas.getData(), EmpresasDTO.class);
		}
		Pagination<EmpresasDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionEmpresas.getTotal());
		pag.setPageSize(paginacionEmpresas.getPageSize());
		pag.setPage(paginacionEmpresas.getPage());
		return pag;
	}

	@Override
	@Transactional()
	public EmpresasDTO insertEmpresa(EmpresasDTO empresasDTO) throws BussinessException {
		Entidad empresa = modelMapper.map(empresasDTO, Entidad.class);
		this.entidadDAO.insertEmpresa(empresa);
		domiciliosDAO.saveUpdateOrDelete(empresa, empresa.getDomicilios(), new HashSet<>());
		contactosPersonaDAO.saveUpdateOrDelete(empresa, empresa.getContactos(), new HashSet<>());
		telefonosPersonaDAO.saveUpdateOrDelete(empresa, empresa.getTelefonos(), new HashSet<>());
		return modelMapper.map(empresa, EmpresasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public EmpresasDTO updateEmpresa(Integer id, EmpresasDTO empresaDTO) throws BussinessException {

		empresaDTO.setId(id);
		Entidad persona = modelMapper.map(empresaDTO, Entidad.class);
		Entidad personaBD = entidadDAO.get(id);
		domiciliosDAO.saveUpdateOrDelete(persona, persona.getDomicilios(), personaBD.getDomicilios());
		contactosPersonaDAO.saveUpdateOrDelete(persona, persona.getContactos(), personaBD.getContactos());
		telefonosPersonaDAO.saveUpdateOrDelete(persona, persona.getTelefonos(), personaBD.getTelefonos());
		entidadDAO.updateEmpresa(personaBD, persona);
		return modelMapper.map(persona, EmpresasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public EmpresasDTO get(Integer id) {
		Entidad empresa = entidadDAO.get(id);
		return modelMapper.map(empresa, EmpresasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void delete(Integer id) throws BussinessException {
		Entidad empresa = entidadDAO.get(id);
		entidadDAO.delete(empresa);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public List<EmpresasMinDTO> getByEmpresaMatch(String q) {
		List<Empresas> empresas = empresasDAO.getByEmpresaMatch(q);
		return modelMapper.mapAsList(empresas, EmpresasMinDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public EmpresasMinDTO getEmpresaMin(Integer id) {
		return modelMapper.map(empresasDAO.getEmpresaMin(id), EmpresasMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public EmpresasMinDTO getByCuit(String cuit) {
		Entidad persona = entidadDAO.getByCuit(cuit);
		return modelMapper.map(persona, EmpresasMinDTO.class);
	}
}
