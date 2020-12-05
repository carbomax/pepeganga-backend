//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:04:08 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.items;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Sdtarticuloswebpagina" type="{PpGg}SDTArticulosWebPagina"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sdtarticuloswebpagina"
})
@XmlRootElement(name = "CargaArticulosPaginado.ExecuteResponse")
public class CargaArticulosPaginadoExecuteResponse {

    @XmlElement(name = "Sdtarticuloswebpagina", required = true)
    protected SDTArticulosWebPagina sdtarticuloswebpagina;

    /**
     * Obtiene el valor de la propiedad sdtarticuloswebpagina.
     * 
     * @return
     *     possible object is
     *     {@link SDTArticulosWebPagina }
     *     
     */
    public SDTArticulosWebPagina getSdtarticuloswebpagina() {
        return sdtarticuloswebpagina;
    }

    /**
     * Define el valor de la propiedad sdtarticuloswebpagina.
     * 
     * @param value
     *     allowed object is
     *     {@link SDTArticulosWebPagina }
     *     
     */
    public void setSdtarticuloswebpagina(SDTArticulosWebPagina value) {
        this.sdtarticuloswebpagina = value;
    }

}
