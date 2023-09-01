/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.exceptions;

import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;
import ar.com.jsuper.utils.Error;

/**
 *
 * @author rafa
 */
public class JSuperException extends DataIntegrityViolationException {

	private Set<Error> error;

	public JSuperException(String msg) {
		super(msg);
	}

	public Set<Error> getError() {
		return error;
	}

	public void setError(Set<Error> error) {
		this.error = error;
	}

}
