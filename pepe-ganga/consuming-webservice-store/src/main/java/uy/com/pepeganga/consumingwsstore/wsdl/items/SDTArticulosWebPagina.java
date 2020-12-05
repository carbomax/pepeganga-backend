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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SDTArticulosWebPagina complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SDTArticulosWebPagina"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Parte" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="Cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Articulos" type="{PpGg}ConsPagSDTArticulosWebPagina.Articulos"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SDTArticulosWebPagina", propOrder = {
    "parte",
    "cantidad",
    "articulos"
})
public class SDTArticulosWebPagina {

    @XmlElement(name = "Parte")
    protected short parte;
    @XmlElement(name = "Cantidad")
    protected int cantidad;
    @XmlElement(name = "Articulos", required = true)
    protected ConsPagSDTArticulosWebPaginaArticulos articulos;

    /**
     * Obtiene el valor de la propiedad parte.
     * 
     */
    public short getParte() {
        return parte;
    }

    /**
     * Define el valor de la propiedad parte.
     * 
     */
    public void setParte(short value) {
        this.parte = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     */
    public void setCantidad(int value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad articulos.
     * 
     * @return
     *     possible object is
     *     {@link ConsPagSDTArticulosWebPaginaArticulos }
     *     
     */
    public ConsPagSDTArticulosWebPaginaArticulos getArticulos() {
        return articulos;
    }

    /**
     * Define el valor de la propiedad articulos.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsPagSDTArticulosWebPaginaArticulos }
     *     
     */
    public void setArticulos(ConsPagSDTArticulosWebPaginaArticulos value) {
        this.articulos = value;
    }

}
