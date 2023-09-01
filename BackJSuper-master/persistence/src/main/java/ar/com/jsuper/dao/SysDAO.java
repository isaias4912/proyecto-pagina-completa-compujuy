package ar.com.jsuper.dao;

import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Message;

public interface SysDAO extends GenericDAO<App, Integer> {

	public Message insert(Message message);
}
