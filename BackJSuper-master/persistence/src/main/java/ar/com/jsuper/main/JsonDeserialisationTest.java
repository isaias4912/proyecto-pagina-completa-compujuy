/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.main;

import ar.com.jsuper.dto.StockSucursalDTO;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 * @author rafa22
 */
public class JsonDeserialisationTest
{

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

//   @Test
    public void allClassesUsedByOurControllersShouldBeDeserialisableByJackson() throws Exception
    {
        assertCanBeMapped(StockSucursalDTO.class);
//        assertCanBeMapped(Project.class);
        // ...
    }

    private void assertCanBeMapped(Class<?> classToTest)
    {
        String message = String.format("%s is not deserialisable, check the swallowed exception in StdDeserializerProvider.hasValueDeserializerFor",
            classToTest.getSimpleName());
//        assertThat(message, converter.canRead(classToTest, MediaType.APPLICATION_JSON), true);
    }

}
