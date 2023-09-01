/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rafa
 */
@Converter
public class TipoClienteConverter implements AttributeConverter<TipoCliente, Integer> {

	public Integer convertToDatabaseColumn(TipoCliente value) {
		if (value == null) {
			return null;
		}

		return value.toInt();
	}

	public TipoCliente convertToEntityAttribute(Integer value) {
		if (value == null) {
			return null;
		}
		return TipoCliente.value(value);
	}
}
