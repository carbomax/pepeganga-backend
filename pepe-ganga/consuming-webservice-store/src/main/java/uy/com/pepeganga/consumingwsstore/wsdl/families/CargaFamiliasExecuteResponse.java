//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:03:02 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.families;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="Sdtlineassubflias" type="{PpGg}ArrayOfSdtLineasSubFlias.SdtLineaSubFlias"/&gt;
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
    "sdtlineassubflias"
})
@XmlRootElement(name = "CargaFamilias.ExecuteResponse")
public class CargaFamiliasExecuteResponse {

    @XmlElement(name = "Sdtlineassubflias", required = true)
    protected ArrayOfSdtLineasSubFliasSdtLineaSubFlias sdtlineassubflias;

    /**
     * Obtiene el valor de la propiedad sdtlineassubflias.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtLineasSubFliasSdtLineaSubFlias }
     *     
     */
    public ArrayOfSdtLineasSubFliasSdtLineaSubFlias getSdtlineassubflias() {
        return sdtlineassubflias;
    }

    /**
     * Define el valor de la propiedad sdtlineassubflias.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtLineasSubFliasSdtLineaSubFlias }
     *     
     */
    public void setSdtlineassubflias(ArrayOfSdtLineasSubFliasSdtLineaSubFlias value) {
        this.sdtlineassubflias = value;
    }

}
