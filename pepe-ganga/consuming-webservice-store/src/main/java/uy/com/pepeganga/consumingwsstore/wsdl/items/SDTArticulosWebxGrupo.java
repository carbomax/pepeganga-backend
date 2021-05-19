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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SDTArticulosWebxGrupo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SDTArticulosWebxGrupo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Consumidor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Empresa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Parte" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="Cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Articulos" type="{PpGg}cons3SDTArticulosWebxGrupo.Articulos"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SDTArticulosWebxGrupo", propOrder = {
    "consumidor",
    "empresa",
    "parte",
    "cantidad",
    "articulos"
})
public class SDTArticulosWebxGrupo {

    @XmlElement(name = "Consumidor", required = true)
    protected String consumidor;
    @XmlElement(name = "Empresa", required = true)
    protected String empresa;
    @XmlElement(name = "Parte")
    protected short parte;
    @XmlElement(name = "Cantidad")
    protected int cantidad;
    @XmlElement(name = "Articulos", required = true)
    protected Cons3SDTArticulosWebxGrupoArticulos articulos;

    /**
     * Obtiene el valor de la propiedad consumidor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumidor() {
        return consumidor;
    }

    /**
     * Define el valor de la propiedad consumidor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumidor(String value) {
        this.consumidor = value;
    }

    /**
     * Obtiene el valor de la propiedad empresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Define el valor de la propiedad empresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpresa(String value) {
        this.empresa = value;
    }

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
     *     {@link Cons3SDTArticulosWebxGrupoArticulos }
     *     
     */
    public Cons3SDTArticulosWebxGrupoArticulos getArticulos() {
        return articulos;
    }

    /**
     * Define el valor de la propiedad articulos.
     * 
     * @param value
     *     allowed object is
     *     {@link Cons3SDTArticulosWebxGrupoArticulos }
     *     
     */
    public void setArticulos(Cons3SDTArticulosWebxGrupoArticulos value) {
        this.articulos = value;
    }

}
