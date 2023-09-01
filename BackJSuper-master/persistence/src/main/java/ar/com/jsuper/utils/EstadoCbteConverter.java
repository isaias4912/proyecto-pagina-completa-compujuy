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
public class EstadoCbteConverter implements AttributeConverter<EstadoCbte, Integer> {

	public Integer convertToDatabaseColumn(EstadoCbte value) {
		if (value == null) {
			return null;
		}

		return value.toInt();
	}

	public EstadoCbte convertToEntityAttribute(Integer value) {
		if (value == null) {
			return null;
		}
		return EstadoCbte.value(value);
	}
}
