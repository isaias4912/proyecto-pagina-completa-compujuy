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
public class DocConverter implements AttributeConverter<Doc, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Doc value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return value.toInt();
	}

	@Override
	public Doc convertToEntityAttribute(Integer value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return Doc.value(value);
	}
}
