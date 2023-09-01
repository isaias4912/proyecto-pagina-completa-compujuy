/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 *
 * @author rafa
 */
@Converter
public class TipoCbteConverter implements AttributeConverter<TipoCbte, Integer> {

	public Integer convertToDatabaseColumn(TipoCbte value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return value.toInt();
	}

	public TipoCbte convertToEntityAttribute(Integer value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return TipoCbte.value(value);
	}
}
