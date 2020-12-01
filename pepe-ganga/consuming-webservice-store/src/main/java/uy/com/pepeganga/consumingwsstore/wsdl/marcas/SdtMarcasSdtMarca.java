//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:03:40 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.marcas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SdtMarcas.SdtMarca complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SdtMarcas.SdtMarca"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MarcaId" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="MarcaDescrip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MarcaEnUso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SdtMarcas.SdtMarca", propOrder = {
    "marcaId",
    "marcaDescrip",
    "marcaEnUso"
})
public class SdtMarcasSdtMarca {

    @XmlElement(name = "MarcaId")
    protected byte marcaId;
    @XmlElement(name = "MarcaDescrip", required = true)
    protected String marcaDescrip;
    @XmlElement(name = "MarcaEnUso", required = true)
    protected String marcaEnUso;

    /**
     * Obtiene el valor de la propiedad marcaId.
     * 
     */
    public byte getMarcaId() {
        return marcaId;
    }

    /**
     * Define el valor de la propiedad marcaId.
     * 
     */
    public void setMarcaId(byte value) {
        this.marcaId = value;
    }

    /**
     * Obtiene el valor de la propiedad marcaDescrip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarcaDescrip() {
        return marcaDescrip;
    }

    /**
     * Define el valor de la propiedad marcaDescrip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarcaDescrip(String value) {
        this.marcaDescrip = value;
    }

    /**
     * Obtiene el valor de la propiedad marcaEnUso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarcaEnUso() {
        return marcaEnUso;
    }

    /**
     * Define el valor de la propiedad marcaEnUso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarcaEnUso(String value) {
        this.marcaEnUso = value;
    }

}
