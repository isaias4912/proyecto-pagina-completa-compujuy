/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.aspect.Precision;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author rafa
 */
public class BigDecimalContextualSerializer extends JsonSerializer<BigDecimal> implements ContextualSerializer {

	private int precision = 0;

	public BigDecimalContextualSerializer(int precision) {
		this.precision = precision;
	}

	public BigDecimalContextualSerializer() {

	}

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (precision == 0) {
			gen.writeNumber(value);
		} else {
			BigDecimal bd =  BigDecimal.ZERO;
			bd = value.setScale(precision, RoundingMode.HALF_UP);
			gen.writeNumber(bd.doubleValue());
		}

	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
		Precision precision = property.getAnnotation(Precision.class);
		if (precision != null) {
			return new BigDecimalContextualSerializer(precision.precision());
		}else{
			return new BigDecimalContextualSerializer(2);
		}
	}
}
