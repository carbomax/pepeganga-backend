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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Sdtpedidoexternocabezal" type="{PpGg}SDTPedidoExternoCabezal"/&gt;
 *         &lt;element name="Sdtpedidoexternorenglones" type="{PpGg}ArrayOfSdtPedidoExternoRenglones.SdtPedExternoRenglon"/&gt;
 *         &lt;element name="Sdtpedidoexternorespuesta" type="{PpGg}SDTPedidoExternoRespuesta"/&gt;
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
    "sdtpedidoexternocabezal",
    "sdtpedidoexternorenglones",
    "sdtpedidoexternorespuesta"
})
@XmlRootElement(name = "CargarPedidoWeb.Execute")
public class CargarPedidoWebExecute {

    @XmlElement(name = "Sdtpedidoexternocabezal", required = true)
    protected SDTPedidoExternoCabezal sdtpedidoexternocabezal;
    @XmlElement(name = "Sdtpedidoexternorenglones", required = true)
    protected ArrayOfSdtPedidoExternoRenglonesSdtPedExternoRenglon sdtpedidoexternorenglones;
    @XmlElement(name = "Sdtpedidoexternorespuesta", required = true)
    protected SDTPedidoExternoRespuesta sdtpedidoexternorespuesta;

    /**
     * Obtiene el valor de la propiedad sdtpedidoexternocabezal.
     * 
     * @return
     *     possible object is
     *     {@link SDTPedidoExternoCabezal }
     *     
     */
    public SDTPedidoExternoCabezal getSdtpedidoexternocabezal() {
        return sdtpedidoexternocabezal;
    }

    /**
     * Define el valor de la propiedad sdtpedidoexternocabezal.
     * 
     * @param value
     *     allowed object is
     *     {@link SDTPedidoExternoCabezal }
     *     
     */
    public void setSdtpedidoexternocabezal(SDTPedidoExternoCabezal value) {
        this.sdtpedidoexternocabezal = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtpedidoexternorenglones.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtPedidoExternoRenglonesSdtPedExternoRenglon }
     *     
     */
    public ArrayOfSdtPedidoExternoRenglonesSdtPedExternoRenglon getSdtpedidoexternorenglones() {
        return sdtpedidoexternorenglones;
    }

    /**
     * Define el valor de la propiedad sdtpedidoexternorenglones.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtPedidoExternoRenglonesSdtPedExternoRenglon }
     *     
     */
    public void setSdtpedidoexternorenglones(ArrayOfSdtPedidoExternoRenglonesSdtPedExternoRenglon value) {
        this.sdtpedidoexternorenglones = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtpedidoexternorespuesta.
     * 
     * @return
     *     possible object is
     *     {@link SDTPedidoExternoRespuesta }
     *     
     */
    public SDTPedidoExternoRespuesta getSdtpedidoexternorespuesta() {
        return sdtpedidoexternorespuesta;
    }

    /**
     * Define el valor de la propiedad sdtpedidoexternorespuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link SDTPedidoExternoRespuesta }
     *     
     */
    public void setSdtpedidoexternorespuesta(SDTPedidoExternoRespuesta value) {
        this.sdtpedidoexternorespuesta = value;
    }

}
