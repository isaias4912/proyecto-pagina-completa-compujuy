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
public class TipoEmpresaConverter implements AttributeConverter<TipoEmpresa, Integer> {

	public Integer convertToDatabaseColumn(TipoEmpresa value) {
		if (value == null) {
			return null;
		}

		return value.toInt();
	}

	public TipoEmpresa convertToEntityAttribute(Integer value) {
		if (value == null) {
			return null;
		}
		return TipoEmpresa.value(value);
	}
}
