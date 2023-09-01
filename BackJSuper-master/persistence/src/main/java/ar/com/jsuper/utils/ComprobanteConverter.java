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
public class ComprobanteConverter implements AttributeConverter<Comprobante, Integer> {

	public Integer convertToDatabaseColumn(Comprobante value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return value.toInt();
	}

	public Comprobante convertToEntityAttribute(Integer value) {
		if (Objects.isNull(value)) {
			return null;
		}
		return Comprobante.value(value);
	}
}
