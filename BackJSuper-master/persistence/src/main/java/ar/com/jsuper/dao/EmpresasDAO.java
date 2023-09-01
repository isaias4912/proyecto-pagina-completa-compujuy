package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.utils.FilterEmpresas;
import java.util.List;

public interface EmpresasDAO extends GenericDAO<Empresas, Integer> {

    Pagination<Empresas> getEmpresasBypage(FilterEmpresas empresaFilter, int page, int pageSize, String fieldOrder, boolean reverse);

    Pagination<Empresas> getEmpresasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

    Empresas getObject(Empresas empresaOld, Empresas empresaNew);

    Empresas updatePersona(Empresas empresaOld, Empresas empresaNew);

    public List<Empresas> getByEmpresaMatch(String q);

    public Empresas getEmpresaMin(Integer id);

}
