package ar.com.jsuper.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ar.com.jsuper.dao.RubrosDAO;
import ar.com.jsuper.dao.TipoPreciosDAO;
import ar.com.jsuper.domain.Rubros;
import ar.com.jsuper.domain.TipoPrecios;

@Repository
public class TipoPreciosDAOImpl extends GenericDAOImpl<TipoPrecios, Integer> implements TipoPreciosDAO {

}
