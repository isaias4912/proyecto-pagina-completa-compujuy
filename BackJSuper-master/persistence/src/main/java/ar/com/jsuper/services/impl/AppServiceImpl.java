package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	AppDAO appDAO;

	@Override
	@Transactional()
	public void deleteVentasByApp(Integer idApp) {
		appDAO.deleteVentasByApp(idApp);
	}

}
