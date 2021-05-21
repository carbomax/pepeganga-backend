//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.05.18 a las 11:14:25 PM UYT 
//


package uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SDTPedidoExternoRespuesta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SDTPedidoExternoRespuesta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ok" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NroPedidoGenerado" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SeriePedidoGenerado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SDTPedidoExternoRespuesta", propOrder = {
    "ok",
    "nroPedidoGenerado",
    "seriePedidoGenerado",
    "mensajeError"
})
public class SDTPedidoExternoRespuesta {

    @XmlElement(name = "Ok", required = true)
    protected String ok;
    @XmlElement(name = "NroPedidoGenerado")
    protected int nroPedidoGenerado;
    @XmlElement(name = "SeriePedidoGenerado", required = true)
    protected String seriePedidoGenerado;
    @XmlElement(name = "MensajeError", required = true)
    protected String mensajeError;

    /**
     * Obtiene el valor de la propiedad ok.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOk() {
        return ok;
    }

    /**
     * Define el valor de la propiedad ok.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOk(String value) {
        this.ok = value;
    }

    /**
     * Obtiene el valor de la propiedad nroPedidoGenerado.
     * 
     */
    public int getNroPedidoGenerado() {
        return nroPedidoGenerado;
    }

    /**
     * Define el valor de la propiedad nroPedidoGenerado.
     * 
     */
    public void setNroPedidoGenerado(int value) {
        this.nroPedidoGenerado = value;
    }

    /**
     * Obtiene el valor de la propiedad seriePedidoGenerado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeriePedidoGenerado() {
        return seriePedidoGenerado;
    }

    /**
     * Define el valor de la propiedad seriePedidoGenerado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeriePedidoGenerado(String value) {
        this.seriePedidoGenerado = value;
    }

    /**
     * Obtiene el valor de la propiedad mensajeError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Define el valor de la propiedad mensajeError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeError(String value) {
        this.mensajeError = value;
    }

}
