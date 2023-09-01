/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author rafael
 */
public class DoubleMoneySerializer extends JsonSerializer<Double> {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@Override
	public void serialize(Double value, com.fasterxml.jackson.core.JsonGenerator jg, com.fasterxml.jackson.databind.SerializerProvider sp) throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
		if (null == value) {
			jg.writeNull();
		} else {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			jg.writeNumber(bd.doubleValue());
		}
	}
}
