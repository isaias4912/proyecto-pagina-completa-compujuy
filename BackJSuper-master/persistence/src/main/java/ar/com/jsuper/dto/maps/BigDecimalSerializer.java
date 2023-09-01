/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 *
 * @author rafa
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        System.out.println("serializer===============:"+t.setScale(2, RoundingMode.DOWN ));
        
//        jg.writeNumber(t.setScale(2, RoundingMode.DOWN ));
        jg.writeNumber(new Double(t.setScale(2, RoundingMode.DOWN ).toString()));
//        jg.writeString( String.format("%.2f", new Double(t.setScale(2, RoundingMode.DOWN ).toString())));
    }

//    @Override
//    public void serialize(Integer tmpInt,
//            JsonGenerator jsonGenerator,
//            SerializerProvider serializerProvider)
//            throws IOException, JsonProcessingException {
//        jsonGenerator.writeObject(tmpInt.toString());
//    }
}
