//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.11.27 a las 05:02:08 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.categories;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfSdtCategorias.SdtCategoria complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSdtCategorias.SdtCategoria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SdtCategorias.SdtCategoria" type="{PpGg}SdtCategorias.SdtCategoria" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSdtCategorias.SdtCategoria", propOrder = {
    "sdtCategoriasSdtCategoria"
})
public class ArrayOfSdtCategoriasSdtCategoria {

    @XmlElement(name = "SdtCategorias.SdtCategoria")
    protected List<SdtCategoriasSdtCategoria> sdtCategoriasSdtCategoria;

    /**
     * Gets the value of the sdtCategoriasSdtCategoria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sdtCategoriasSdtCategoria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSdtCategoriasSdtCategoria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SdtCategoriasSdtCategoria }
     * 
     * 
     */
    public List<SdtCategoriasSdtCategoria> getSdtCategoriasSdtCategoria() {
        if (sdtCategoriasSdtCategoria == null) {
            sdtCategoriasSdtCategoria = new ArrayList<SdtCategoriasSdtCategoria>();
        }
        return this.sdtCategoriasSdtCategoria;
    }

}
