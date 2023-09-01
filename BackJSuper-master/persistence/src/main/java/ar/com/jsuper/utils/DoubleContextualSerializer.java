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
public class DoubleContextualSerializer extends JsonSerializer<Double> implements ContextualSerializer {

	private int precision = 0;

	public DoubleContextualSerializer(int precision) {
		this.precision = precision;
	}

	public DoubleContextualSerializer() {

	}

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (precision == 0) {
			gen.writeNumber(value.doubleValue());
		} else {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(precision, RoundingMode.HALF_UP);
			gen.writeNumber(bd.doubleValue());
		}

	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
		Precision precision = property.getAnnotation(Precision.class);
		if (precision != null) {
			return new DoubleContextualSerializer(precision.precision());
		}else{
			return new DoubleContextualSerializer(2);
		}
	}
}
