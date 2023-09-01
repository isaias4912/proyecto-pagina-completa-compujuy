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
public class AsociadaConverter implements AttributeConverter<Asociada, Integer> {

	public Integer convertToDatabaseColumn(Asociada value) {
		if (value == null) {
			return null;
		}

		return value.toInt();
	}

	public Asociada convertToEntityAttribute(Integer value) {
		if (value == null) {
			return null;
		}
		return Asociada.value(value);
	}
}
