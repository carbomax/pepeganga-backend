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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfSdtSubFlias.SdtSubFlia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSdtSubFlias.SdtSubFlia"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SdtSubFlias.SdtSubFlia" type="{PpGg}SdtSubFlias.SdtSubFlia" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSdtSubFlias.SdtSubFlia", propOrder = {
    "sdtSubFliasSdtSubFlia"
})
public class ArrayOfSdtSubFliasSdtSubFlia {

    @XmlElement(name = "SdtSubFlias.SdtSubFlia")
    protected List<SdtSubFliasSdtSubFlia> sdtSubFliasSdtSubFlia;

    /**
     * Gets the value of the sdtSubFliasSdtSubFlia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sdtSubFliasSdtSubFlia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSdtSubFliasSdtSubFlia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SdtSubFliasSdtSubFlia }
     * 
     * 
     */
    public List<SdtSubFliasSdtSubFlia> getSdtSubFliasSdtSubFlia() {
        if (sdtSubFliasSdtSubFlia == null) {
            sdtSubFliasSdtSubFlia = new ArrayList<SdtSubFliasSdtSubFlia>();
        }
        return this.sdtSubFliasSdtSubFlia;
    }

}
