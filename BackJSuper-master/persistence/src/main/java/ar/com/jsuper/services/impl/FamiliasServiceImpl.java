package ar.com.jsuper.services.impl;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.FamiliasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.FamiliasUtil;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.dto.FamiliaDTO;
import ar.com.jsuper.dto.FamiliaMinDTO;
import ar.com.jsuper.dto.FamiliaPadreDTO;
import ar.com.jsuper.dto.FamiliasArbolDTO;
import ar.com.jsuper.dto.FamiliasDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.FamiliasService;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamiliasServiceImpl implements FamiliasService {

	@Autowired
	private FamiliasDAO familiasDAO;

	@Autowired
	private OrikaBeanMapper modelMapper;

	@Transactional(rollbackFor = BussinessException.class)
	public FamiliaPadreDTO insertFamilia(FamiliaPadreDTO familiaPadreDTO) throws BussinessException {
		Familias familia = modelMapper.map(familiaPadreDTO, Familias.class);
		familia = familiasDAO.insert(familia);
		return modelMapper.map(familia, FamiliaPadreDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public FamiliaPadreDTO updateFamilia(Integer id, FamiliaPadreDTO familiaPadreDTO) throws BussinessException {
		familiaPadreDTO.setId(id);
		Familias familia = modelMapper.map(familiaPadreDTO, Familias.class);
		Familias familiaBD = familiasDAO.get(id);
		familia = familiasDAO.update(familiaBD, familia);
		return modelMapper.map(familia, FamiliaPadreDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void deleteFamilia(FamiliaMinDTO familiaMinDTO) throws BussinessException {
		Familias familia = modelMapper.map(familiaMinDTO, Familias.class);
		familiasDAO.delete(familia);
	}

	@Transactional(rollbackFor = BussinessException.class)
	public Familias getFamilia(Integer id) throws BussinessException {
		try {
			return familiasDAO.get(id);
		} catch (Exception e) {
			throw new BussinessException(e);
		}
	}

	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Set<FamiliasDTO> getall() throws BussinessException {
		try {
			Set<Familias> familias = familiasDAO.getAll();
			return modelMapper.mapAsSet(familias, FamiliasDTO.class);
		} catch (Exception e) {
			throw new BussinessException(e);
		}
	}

	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Set<FamiliaDTO> getByName(String name) throws BussinessException {
		try {
			Set<Familias> setF = familiasDAO.getByName(name);
			Set<FamiliaDTO> listaFamilias = new HashSet<>();
			for (Familias familia : setF) {
				FamiliaDTO orderDTO = modelMapper.map(familia, FamiliaDTO.class);
				if (familia.getFamilia() != null) {
					String shortPath = getPathShortName(familia);
					String[] arrayShortPath = shortPath.split("\\-");
					ArrayUtils.reverse(arrayShortPath);
					shortPath = "";
					for (String string : arrayShortPath) {
						shortPath += string + "-";
					}
					shortPath = shortPath.substring(0, shortPath.length() - 1);
					orderDTO.setPath(shortPath);
				} else {
					orderDTO.setPath(familia.getNombreCorto());
				}

				listaFamilias.add(orderDTO);
			}
			return listaFamilias;
		} catch (Exception e) {
			throw new BussinessException(e);
		}
	}

	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Set<FamiliasDTO> getAllByShortName() throws BussinessException {
		try {
			Set<Familias> setF = familiasDAO.getAll();
//            Set<FamiliasDTO> listaFamilias = new HashSet<>();
//                        FamiliasDTO orderDTO = modelMapper.map(familia, FamiliasDTO.class);
//			for (Familias familia : setF) {
//				FamiliasDTO orderDTO = modelMapper.map(familia, FamiliasDTO.class);
//				if (familia.getFamilia() != null) {
//					String shortPath=getPathShortName(familia);
//					String[] arrayShortPath= shortPath.split("\\-");
//					ArrayUtils.reverse(arrayShortPath);
//					shortPath="";
//					for (String string : arrayShortPath) {
//						shortPath +=string+"-";
//					}
//					shortPath = shortPath.substring(0, shortPath.length()-1);
//					orderDTO.setPath(shortPath);
//				} else {
//					orderDTO.setPath(familia.getNombreCorto());
//				}

//				listaFamilias.add(orderDTO);
//			}
//            Type targetListType = new TypeToken<Set<FamiliasDTO>>() {
//            }.getType();
//            Set<FamiliasDTO> personDTOs = modelMapper.map(setF, targetListType);
			return modelMapper.mapAsSet(setF, FamiliasDTO.class);

			// TypeToken<T>
			// listaFamilias = modelMapper.map(familiasDAO.getAll(),
			// listaFamilias);
//            return personDTOs;
			// return familiasDAO.getAll();
		} catch (Exception e) {
			throw new BussinessException(e);
		}
	}

	private String getPathShortName(Familias familia) {
		String nombreCorto = "";
		if (familia.getFamilia() != null) {
			nombreCorto = familia.getNombreCorto() + "-" + getPathShortName(familia.getFamilia());
		} else {
			nombreCorto = familia.getNombreCorto();
		}
		return nombreCorto;
	}

	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public String getIdOfChildsByFamilia(Familias familia) throws BussinessException {
		try {
			Familias f1 = this.getFamilia(familia.getId());
			return FamiliasUtil.getIdOfChildsByFamilia(f1);
		} catch (Exception e) {
			throw new BussinessException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Set<FamiliasArbolDTO> getAllMayorLevel() throws BussinessException {
		Set<Familias> familias = familiasDAO.getAllMayorLevel();
		return modelMapper.mapAsSet(familias, FamiliasArbolDTO.class);

	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public FamiliaPadreDTO get(Integer id) throws BussinessException {
		Familias familia = familiasDAO.get(id);
		return modelMapper.map(familia, FamiliaPadreDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public FamiliaMinDTO getFamiliaMin(Integer id) throws BussinessException {
		Familias familia = familiasDAO.get(id);
		return modelMapper.map(familia, FamiliaMinDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<FamiliaDTO> getFamiliasBypage(FilterGenericDTO familiaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		FilterGeneric filterfamilia = modelMapper.map(familiaFilter, FilterGeneric.class);
		// armamos los filtros extras para los daos
		Pagination<Familias> paginacionFamilia = familiasDAO.getFamiliasBypage(filterfamilia, page, pageSize, fieldOrder, reverse);
		List<FamiliaDTO> lista = new ArrayList<>();
		if (paginacionFamilia.getData() != null) {
			lista = modelMapper.mapAsList(paginacionFamilia.getData(), FamiliaDTO.class);
		}
		Pagination<FamiliaDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionFamilia.getTotal());
		pag.setPageSize(paginacionFamilia.getPageSize());
		pag.setPage(paginacionFamilia.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExistNombreCorto(String nombreCorto) {
		return familiasDAO.isExistNombreCorto(nombreCorto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FamiliaMinDTO> getFamiliasMin(Set<Integer> ids) {
		List<Familias> productos = this.familiasDAO.getFamiliasMin(ids);
		return modelMapper.mapAsList(productos, FamiliaMinDTO.class);
	}
}
