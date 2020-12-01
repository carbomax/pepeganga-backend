//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:03:40 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.marcas;

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
 *         &lt;element name="Sdtmarcas" type="{PpGg}ArrayOfSdtMarcas.SdtMarca"/&gt;
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
    "sdtmarcas"
})
@XmlRootElement(name = "CargaMarcas.ExecuteResponse")
public class CargaMarcasExecuteResponse {

    @XmlElement(name = "Sdtmarcas", required = true)
    protected ArrayOfSdtMarcasSdtMarca sdtmarcas;

    /**
     * Obtiene el valor de la propiedad sdtmarcas.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtMarcasSdtMarca }
     *     
     */
    public ArrayOfSdtMarcasSdtMarca getSdtmarcas() {
        return sdtmarcas;
    }

    /**
     * Define el valor de la propiedad sdtmarcas.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtMarcasSdtMarca }
     *     
     */
    public void setSdtmarcas(ArrayOfSdtMarcasSdtMarca value) {
        this.sdtmarcas = value;
    }

}
