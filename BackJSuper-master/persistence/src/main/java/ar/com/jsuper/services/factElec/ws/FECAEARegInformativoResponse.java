
package ar.com.jsuper.services.factElec.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FECAEARegInformativoResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEAResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fecaeaRegInformativoResult"
})
@XmlRootElement(name = "FECAEARegInformativoResponse")
public class FECAEARegInformativoResponse {

    @XmlElement(name = "FECAEARegInformativoResult")
    protected FECAEAResponse fecaeaRegInformativoResult;

    /**
     * Obtiene el valor de la propiedad fecaeaRegInformativoResult.
     * 
     * @return
     *     possible object is
     *     {@link FECAEAResponse }
     *     
     */
    public FECAEAResponse getFECAEARegInformativoResult() {
        return fecaeaRegInformativoResult;
    }

    /**
     * Define el valor de la propiedad fecaeaRegInformativoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEAResponse }
     *     
     */
    public void setFECAEARegInformativoResult(FECAEAResponse value) {
        this.fecaeaRegInformativoResult = value;
    }

}
