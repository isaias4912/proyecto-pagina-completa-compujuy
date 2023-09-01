/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rafa
 */
@Converter
public class OrigenConverter implements AttributeConverter<Origen, Integer> {

	public Integer convertToDatabaseColumn(Origen value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return value.toInt();
	}

	public Origen convertToEntityAttribute(Integer value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return Origen.value(value);
	}
}
