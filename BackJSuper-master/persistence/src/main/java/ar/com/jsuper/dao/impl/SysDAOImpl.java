package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.SysDAO;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Message;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class SysDAOImpl extends GenericDAOImpl<App, Integer> implements SysDAO {

	@Override
	public Message insert(Message message) {
		Session session = sessionFactory.getCurrentSession();
		session.save(message);
		return message;
	}

}
