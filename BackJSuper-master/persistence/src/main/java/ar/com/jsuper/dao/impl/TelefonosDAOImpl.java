package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Telefonos;
import ar.com.jsuper.domain.Personas;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;
import ar.com.jsuper.dao.TelefonosDAO;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;

@Repository
public class TelefonosDAOImpl extends GenericDAOImpl<Telefonos, Integer> implements TelefonosDAO {

	@Override
	public void saveUpdateOrDelete(Entidad persona, Set<Telefonos> telefonos, Set<Telefonos> telefonosBD) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Boolean encontro = false;
		Telefonos telTemp = null;
		if (Objects.nonNull(telefonos)) {
			for (Telefonos tel : telefonos) {
				encontro = false;
				for (Telefonos telBD : telefonosBD) {
					if (Objects.equals(tel.getId(), telBD.getId())) {
						encontro = true;
						telTemp = this.getObject(telBD, tel);
					}
				}
				if (encontro) {
					session.update(telTemp);
				} else {
					tel.setEntidad(persona);
//                tel.setEntidad("persona");
					session.save(tel);
				}
			}
			for (Telefonos telBD : telefonosBD) {
				encontro = false;
				for (Telefonos cb : telefonos) {
					if (Objects.equals(cb.getId(), telBD.getId())) {
						encontro = true;
					}
				}
				if (!encontro) {
					session.delete(telBD);
				}
			}
		}
	}
//    @Override
//    public void saveUpdateOrDelete(Empresas empresa, Set<Telefonos> telefonos, Set<Telefonos> telefonosBD) throws BussinessException {
//        Session session = sessionFactory.getCurrentSession();
//        Boolean encontro = false;
//        Telefonos telTemp = null;
//        for (Telefonos tel : telefonos) {
//            encontro = false;
//            for (Telefonos telBD : telefonosBD) {
//                if (Objects.equals(tel.getId(), telBD.getId())) {
//                    encontro = true;
//                    telTemp = this.getObject(telBD, tel);
//                }
//            }
//            if (encontro) {
//                session.update(telTemp);
//            } else {
//                tel.setEmpresa(empresa);
//                tel.setEntidad("empresa");
//                session.save(tel);
//            }
//        }
//        for (Telefonos telBD : telefonosBD) {
//            encontro = false;
//            for (Telefonos cb : telefonos) {
//                if (Objects.equals(cb.getId(), telBD.getId())) {
//                    encontro = true;
//                }
//            }
//            if (!encontro) {
//                session.delete(telBD);
//            }
//        }
//    }

	@Override
	public Telefonos getObject(Telefonos telefonoOld, Telefonos telefonoNew) {
		if (Objects.isNull(telefonoOld)) {
			telefonoOld = new Telefonos();
		}
		telefonoOld.setDetalle(telefonoNew.getDetalle());
		telefonoOld.setEmpresaTel(telefonoNew.getEmpresaTel());
		telefonoOld.setNumero(telefonoNew.getNumero());
		telefonoOld.setPrefijo(telefonoNew.getPrefijo());
		telefonoOld.setTipo(telefonoNew.getTipo());
		telefonoOld.setPrincipal(telefonoNew.getPrincipal());
		return telefonoOld;
	}

}
