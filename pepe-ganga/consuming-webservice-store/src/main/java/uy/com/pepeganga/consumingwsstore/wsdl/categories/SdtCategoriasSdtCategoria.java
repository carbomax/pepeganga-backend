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


/**
 * <p>Clase Java para SdtCategorias.SdtCategoria complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SdtCategorias.SdtCategoria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CategoriaId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="CategoriaDescrip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SdtCategorias.SdtCategoria", propOrder = {
    "categoriaId",
    "categoriaDescrip"
})
public class SdtCategoriasSdtCategoria {

    @XmlElement(name = "CategoriaId")
    protected short categoriaId;
    @XmlElement(name = "CategoriaDescrip", required = true)
    protected String categoriaDescrip;

    /**
     * Obtiene el valor de la propiedad categoriaId.
     * 
     */
    public short getCategoriaId() {
        return categoriaId;
    }

    /**
     * Define el valor de la propiedad categoriaId.
     * 
     */
    public void setCategoriaId(short value) {
        this.categoriaId = value;
    }

    /**
     * Obtiene el valor de la propiedad categoriaDescrip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaDescrip() {
        return categoriaDescrip;
    }

    /**
     * Define el valor de la propiedad categoriaDescrip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaDescrip(String value) {
        this.categoriaDescrip = value;
    }

}
