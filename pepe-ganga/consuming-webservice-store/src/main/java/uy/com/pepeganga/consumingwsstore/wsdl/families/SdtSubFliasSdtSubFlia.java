//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:03:02 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.families;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SdtSubFlias.SdtSubFlia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SdtSubFlias.SdtSubFlia"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SubFliaId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="SubFliaDsc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SdtSubFlias.SdtSubFlia", propOrder = {
    "subFliaId",
    "subFliaDsc"
})
public class SdtSubFliasSdtSubFlia {

    @XmlElement(name = "SubFliaId")
    protected short subFliaId;
    @XmlElement(name = "SubFliaDsc", required = true)
    protected String subFliaDsc;

    /**
     * Obtiene el valor de la propiedad subFliaId.
     * 
     */
    public short getSubFliaId() {
        return subFliaId;
    }

    /**
     * Define el valor de la propiedad subFliaId.
     * 
     */
    public void setSubFliaId(short value) {
        this.subFliaId = value;
    }

    /**
     * Obtiene el valor de la propiedad subFliaDsc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubFliaDsc() {
        return subFliaDsc;
    }

    /**
     * Define el valor de la propiedad subFliaDsc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubFliaDsc(String value) {
        this.subFliaDsc = value;
    }

}
