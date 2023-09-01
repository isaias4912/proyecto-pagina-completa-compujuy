package ar.com.jsuper.api.utils;

import org.springframework.http.HttpStatus;

public class MessageError {
	private HttpStatus code;
	private String message;
	private String messageException;

		
	public MessageError(HttpStatus internalServerError, String message, String messageException) {
		super();
		this.code = internalServerError;
		this.message = message;
		this.messageException = messageException;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageException() {
		return messageException;
	}

	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}

}
