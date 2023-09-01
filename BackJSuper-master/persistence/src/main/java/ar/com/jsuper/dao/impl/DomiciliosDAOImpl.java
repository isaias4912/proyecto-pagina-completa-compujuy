package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.DomiciliosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Domicilios;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;

@Repository
public class DomiciliosDAOImpl extends GenericDAOImpl<Domicilios, Integer> implements DomiciliosDAO {

	@Override
	public void saveUpdateOrDelete(Entidad entidad, Set<Domicilios> domicilios, Set<Domicilios> domiciliosBD) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Boolean encontro = false;
		Domicilios cbTemp = null;
		if (Objects.nonNull(domicilios)) {
			for (Domicilios dom : domicilios) {
				encontro = false;
				for (Domicilios domBD : domiciliosBD) {
					if (Objects.equals(dom.getId(), domBD.getId())) {
						encontro = true;
						cbTemp = this.getObject(domBD, dom);
					}
				}
				if (encontro) {
					session.update(cbTemp);
				} else {
//                dom.setEntidad("persona");// es de persona esta entidad
					dom.setEntidad(entidad);
					session.save(dom);
				}
			}
			for (Domicilios cbBD : domiciliosBD) {
				encontro = false;
				for (Domicilios cb : domicilios) {
					if (Objects.equals(cb.getId(), cbBD.getId())) {
						encontro = true;
					}
				}
				if (!encontro) {
					session.delete(cbBD);
				}
			}
		}
	}
//    @Override
//    public void saveUpdateOrDelete(Empresas empresa, Set<Domicilios> domicilios, Set<Domicilios> domiciliosBD) throws BussinessException {
//        Session session = sessionFactory.getCurrentSession();
//        Boolean encontro = false;
//        Domicilios cbTemp = null;
//        for (Domicilios dom : domicilios) {
//            encontro = false;
//            for (Domicilios domBD : domiciliosBD) {
//                if (Objects.equals(dom.getId(), domBD.getId())) {
//                    encontro = true;
//                    cbTemp = this.getObject(domBD, dom);
//                }
//            }
//            if (encontro) {
//                session.update(cbTemp);
//            } else {
//                dom.setEntidad("empresa");// es de empresa
//                dom.setEmpresa(empresa);
//                session.save(dom);
//            }
//        }
//        for (Domicilios cbBD : domiciliosBD) {
//            encontro = false;
//            for (Domicilios cb : domicilios) {
//                if (Objects.equals(cb.getId(), cbBD.getId())) {
//                    encontro = true;
//                }
//            }
//            if (!encontro) {
//                session.delete(cbBD);
//            }
//        }
//    }

	@Override
	public Domicilios getObject(Domicilios domicilioOld, Domicilios domicilioNew) {
		if (Objects.isNull(domicilioOld)) {
			domicilioOld = new Domicilios();
		}
		domicilioOld.setCalle(domicilioNew.getCalle());
		domicilioOld.setNumero(domicilioNew.getNumero());
		domicilioOld.setPiso(domicilioNew.getPiso());
		domicilioOld.setDpto(domicilioNew.getDpto());
		domicilioOld.setProvincia(domicilioNew.getProvincia());
		domicilioOld.setCiudad(domicilioNew.getCiudad());
		domicilioOld.setDetalle(domicilioNew.getDetalle());
		domicilioOld.setPrincipal(domicilioNew.getPrincipal());
		return domicilioOld;
	}

}
