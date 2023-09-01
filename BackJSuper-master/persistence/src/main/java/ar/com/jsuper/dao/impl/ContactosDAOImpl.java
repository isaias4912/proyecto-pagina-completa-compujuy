package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Personas;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;
import ar.com.jsuper.dao.ContactosDAO;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Proveedores;
import java.util.Set;

@Repository
public class ContactosDAOImpl extends GenericDAOImpl<Contactos, Integer> implements ContactosDAO {

	@Override
	public void saveUpdateOrDelete(Entidad entidad, Set<Contactos> contactos, Set<Contactos> contactosBD) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Boolean encontro = false;
		Contactos cbTemp = null;
		if (Objects.nonNull(contactos)) {
			for (Contactos cont : contactos) {
				encontro = false;
				for (Contactos domBD : contactosBD) {
					if (Objects.equals(cont.getId(), domBD.getId())) {
						encontro = true;
						cbTemp = this.getObject(domBD, cont);
					}
				}
				if (encontro) {
					session.update(cbTemp);
				} else {
					cont.setEntidad(entidad);
//                cont.setEntidad("persona");
					session.save(cont);
				}
			}
			for (Contactos contBD : contactosBD) {
				encontro = false;
				for (Contactos cb : contactos) {
					if (Objects.equals(cb.getId(), contBD.getId())) {
						encontro = true;
					}
				}
				if (!encontro) {
					session.delete(contBD);
				}
			}
		}
	}
//    @Override
//    public void saveUpdateOrDelete(Empresas empresa, Set<Contactos> contactos, Set<Contactos> contactosBD) throws BussinessException {
//        Session session = sessionFactory.getCurrentSession();
//        Boolean encontro = false;
//        Contactos cbTemp = null;
//        for (Contactos cont : contactos) {
//            encontro = false;
//            for (Contactos domBD : contactosBD) {
//                if (Objects.equals(cont.getId(), domBD.getId())) {
//                    encontro = true;
//                    cbTemp = this.getObject(domBD, cont);
//                }
//            }
//            if (encontro) {
//                session.update(cbTemp);
//            } else {
//                cont.setEmpresa(empresa);
//                cont.setEntidad("empresa");
//                session.save(cont);
//            }
//        }
//        for (Contactos contBD : contactosBD) {
//            encontro = false;
//            for (Contactos cb : contactos) {
//                if (Objects.equals(cb.getId(), contBD.getId())) {
//                    encontro = true;
//                }
//            }
//            if (!encontro) {
//                session.delete(contBD);
//            }
//        }
//    }

	@Override
	public Contactos getObject(Contactos contactoOld, Contactos contactoNew) {
		if (Objects.isNull(contactoOld)) {
			contactoOld = new Contactos();
		}
		contactoOld.setDescripcion(contactoNew.getDescripcion());
		contactoOld.setDetalle(contactoNew.getDetalle());
		contactoOld.setSubtipo(contactoNew.getSubtipo());
		contactoOld.setTipo(contactoNew.getTipo());
		return contactoOld;
	}

}
