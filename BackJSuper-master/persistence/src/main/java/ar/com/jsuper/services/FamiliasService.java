package ar.com.jsuper.services;

import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.dto.FamiliaDTO;
import ar.com.jsuper.dto.FamiliaMinDTO;
import ar.com.jsuper.dto.FamiliaPadreDTO;
import ar.com.jsuper.dto.FamiliasArbolDTO;
import ar.com.jsuper.dto.FamiliasDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import java.util.List;

public interface FamiliasService {

	FamiliaPadreDTO insertFamilia(FamiliaPadreDTO familia) throws BussinessException;

	FamiliaPadreDTO updateFamilia(Integer id, FamiliaPadreDTO familia) throws BussinessException;

	void deleteFamilia(FamiliaMinDTO familiaMinDTO) throws BussinessException;

	Familias getFamilia(Integer id) throws BussinessException;

	public FamiliaPadreDTO get(Integer id) throws BussinessException;

	public FamiliaMinDTO getFamiliaMin(Integer id) throws BussinessException;

	Set<FamiliasDTO> getall() throws BussinessException;

	Set<FamiliasDTO> getAllByShortName() throws BussinessException;

	Set<FamiliaDTO> getByName(String name) throws BussinessException;

	Set<FamiliasArbolDTO> getAllMayorLevel() throws BussinessException;

	String getIdOfChildsByFamilia(Familias familia) throws BussinessException;

	Pagination<FamiliaDTO> getFamiliasBypage(FilterGenericDTO familiaFilter, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

	boolean isExistNombreCorto(String nombreCorto);

	public List<FamiliaMinDTO> getFamiliasMin(Set<Integer> ids);

}
