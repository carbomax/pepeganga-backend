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
 * <p>Clase Java para SdtLineasSubFlias.SdtLineaSubFlias complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SdtLineasSubFlias.SdtLineaSubFlias"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LinId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="LinDsc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SdtSubFlias" type="{PpGg}ArrayOfSdtSubFlias.SdtSubFlia"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SdtLineasSubFlias.SdtLineaSubFlias", propOrder = {
    "linId",
    "linDsc",
    "sdtSubFlias"
})
public class SdtLineasSubFliasSdtLineaSubFlias {

    @XmlElement(name = "LinId")
    protected short linId;
    @XmlElement(name = "LinDsc", required = true)
    protected String linDsc;
    @XmlElement(name = "SdtSubFlias", required = true)
    protected ArrayOfSdtSubFliasSdtSubFlia sdtSubFlias;

    /**
     * Obtiene el valor de la propiedad linId.
     * 
     */
    public short getLinId() {
        return linId;
    }

    /**
     * Define el valor de la propiedad linId.
     * 
     */
    public void setLinId(short value) {
        this.linId = value;
    }

    /**
     * Obtiene el valor de la propiedad linDsc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinDsc() {
        return linDsc;
    }

    /**
     * Define el valor de la propiedad linDsc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinDsc(String value) {
        this.linDsc = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtSubFlias.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtSubFliasSdtSubFlia }
     *     
     */
    public ArrayOfSdtSubFliasSdtSubFlia getSdtSubFlias() {
        return sdtSubFlias;
    }

    /**
     * Define el valor de la propiedad sdtSubFlias.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtSubFliasSdtSubFlia }
     *     
     */
    public void setSdtSubFlias(ArrayOfSdtSubFliasSdtSubFlia value) {
        this.sdtSubFlias = value;
    }

}
