//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.05.18 a las 10:12:03 PM UYT 
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
 *         &lt;element name="Sdtarticuloswebxgrupo" type="{PpGg}SDTArticulosWebxGrupo"/&gt;
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
    "sdtarticuloswebxgrupo"
})
@XmlRootElement(name = "CargaArticulosPPyK.ExecuteResponse")
public class CargaArticulosPPyKExecuteResponse {

    @XmlElement(name = "Sdtarticuloswebxgrupo", required = true)
    protected SDTArticulosWebxGrupo sdtarticuloswebxgrupo;

    /**
     * Obtiene el valor de la propiedad sdtarticuloswebxgrupo.
     * 
     * @return
     *     possible object is
     *     {@link SDTArticulosWebxGrupo }
     *     
     */
    public SDTArticulosWebxGrupo getSdtarticuloswebxgrupo() {
        return sdtarticuloswebxgrupo;
    }

    /**
     * Define el valor de la propiedad sdtarticuloswebxgrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link SDTArticulosWebxGrupo }
     *     
     */
    public void setSdtarticuloswebxgrupo(SDTArticulosWebxGrupo value) {
        this.sdtarticuloswebxgrupo = value;
    }

}
