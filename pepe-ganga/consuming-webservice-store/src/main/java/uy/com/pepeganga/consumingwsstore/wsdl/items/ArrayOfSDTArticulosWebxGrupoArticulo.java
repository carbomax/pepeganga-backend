//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.05.18 a las 10:12:03 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.items;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfSDTArticulosWebxGrupo.Articulo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSDTArticulosWebxGrupo.Articulo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SDTArticulosWebxGrupo.Articulo" type="{PpGg}SDTArticulosWebxGrupo.Articulo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSDTArticulosWebxGrupo.Articulo", propOrder = {
    "sdtArticulosWebxGrupoArticulo"
})
public class ArrayOfSDTArticulosWebxGrupoArticulo {

    @XmlElement(name = "SDTArticulosWebxGrupo.Articulo")
    protected List<SDTArticulosWebxGrupoArticulo> sdtArticulosWebxGrupoArticulo;

    /**
     * Gets the value of the sdtArticulosWebxGrupoArticulo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sdtArticulosWebxGrupoArticulo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSDTArticulosWebxGrupoArticulo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SDTArticulosWebxGrupoArticulo }
     * 
     * 
     */
    public List<SDTArticulosWebxGrupoArticulo> getSDTArticulosWebxGrupoArticulo() {
        if (sdtArticulosWebxGrupoArticulo == null) {
            sdtArticulosWebxGrupoArticulo = new ArrayList<SDTArticulosWebxGrupoArticulo>();
        }
        return this.sdtArticulosWebxGrupoArticulo;
    }

}
