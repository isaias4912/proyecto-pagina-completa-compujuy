package ar.com.jsuper.dao.exception;

import ar.com.jsuper.aspect.LoggingAspect;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

public class DataException extends DataIntegrityViolationException {

	private Logger logger = Logger.getLogger(LoggingAspect.class);
	private Object data;
	private String message;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DataException(Object data, String message) {
		super(message);
		this.data = data;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
