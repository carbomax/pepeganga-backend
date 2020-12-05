//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:02:08 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.categories;

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
 *         &lt;element name="Sdtcategorias" type="{PpGg}ArrayOfSdtCategorias.SdtCategoria"/&gt;
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
    "sdtcategorias"
})
@XmlRootElement(name = "CargaZafras.ExecuteResponse")
public class CargaZafrasExecuteResponse {

    @XmlElement(name = "Sdtcategorias", required = true)
    protected ArrayOfSdtCategoriasSdtCategoria sdtcategorias;

    /**
     * Obtiene el valor de la propiedad sdtcategorias.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtCategoriasSdtCategoria }
     *     
     */
    public ArrayOfSdtCategoriasSdtCategoria getSdtcategorias() {
        return sdtcategorias;
    }

    /**
     * Define el valor de la propiedad sdtcategorias.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtCategoriasSdtCategoria }
     *     
     */
    public void setSdtcategorias(ArrayOfSdtCategoriasSdtCategoria value) {
        this.sdtcategorias = value;
    }

}
