package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.domain.utils.IAfip;
import java.util.List;

public interface EntidadDAO extends GenericDAO<Entidad, Integer> {

	Pagination<Entidad> getEntidadesBypage(FilterEntidad filterEntidad, int page, int pageSize, String fieldOrder, boolean reverse);

	public Pagination<Entidad> getEntidadesByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	public Pagination<Entidad> getEntidadesPerByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	public Pagination<Entidad> getEntidadesByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	public List<Entidad> getEntidadesAndEmail(String query);

	public Entidad getByDni(String dni);

	public Entidad getByCuit(String cuit);

	public Entidad insertPersona(Entidad entidad);

	public Entidad insertEmpresa(Entidad entidad);

	public Entidad updatePersona(Entidad personaOld, Entidad personaNew);

	public Entidad updateEmpresa(Entidad personaOld, Entidad personaNew);

	public Entidad getObjectPersona(Entidad entidadOld, Entidad entidadNew);

	public Entidad getObjectEmpresa(Entidad entidadOld, Entidad entidadNew);

	public Boolean isCliente(Integer id);

	public Boolean isProveedor(Integer id);

	public void setDoc(Entidad entidad, IAfip cliente);

}
