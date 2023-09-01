/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import java.math.BigDecimal;


/**
 *
 * @author rafael
 */
public class MoneySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal t, com.fasterxml.jackson.core.JsonGenerator jg, com.fasterxml.jackson.databind.SerializerProvider sp) throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
        jg.writeNumber(t.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
