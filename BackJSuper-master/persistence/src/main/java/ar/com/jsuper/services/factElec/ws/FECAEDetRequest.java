
package ar.com.jsuper.services.factElec.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FECAEDetRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="FECAEDetRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ar.gov.afip.dif.FEV1/}FEDetRequest">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAEDetRequest")
@XmlSeeAlso({
    FECompConsResponse.class
})
public class FECAEDetRequest
    extends FEDetRequest
{


}
