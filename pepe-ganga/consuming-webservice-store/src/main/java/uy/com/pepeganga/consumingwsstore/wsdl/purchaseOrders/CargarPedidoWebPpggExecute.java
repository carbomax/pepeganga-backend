//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.02.02 a las 08:02:42 PM UYT 
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
 *         &lt;element name="Sdtpedidoppggcabezal" type="{PpGg}SDTPedidoPpggCabezal"/&gt;
 *         &lt;element name="Sdtpedidoppggrenglones" type="{PpGg}ArrayOfSdtPedidoPpggRenglones.SdtPedPpggRenglon"/&gt;
 *         &lt;element name="Sdtpedidoppggrespuesta" type="{PpGg}SdtPedidoPpggRespuesta"/&gt;
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
    "sdtpedidoppggcabezal",
    "sdtpedidoppggrenglones",
    "sdtpedidoppggrespuesta"
})
@XmlRootElement(name = "CargarPedidoWebPpgg.Execute")
public class CargarPedidoWebPpggExecute {

    @XmlElement(name = "Sdtpedidoppggcabezal", required = true)
    protected SDTPedidoPpggCabezal sdtpedidoppggcabezal;
    @XmlElement(name = "Sdtpedidoppggrenglones", required = true)
    protected ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon sdtpedidoppggrenglones;
    @XmlElement(name = "Sdtpedidoppggrespuesta", required = true)
    protected SdtPedidoPpggRespuesta sdtpedidoppggrespuesta;

    /**
     * Obtiene el valor de la propiedad sdtpedidoppggcabezal.
     * 
     * @return
     *     possible object is
     *     {@link SDTPedidoPpggCabezal }
     *     
     */
    public SDTPedidoPpggCabezal getSdtpedidoppggcabezal() {
        return sdtpedidoppggcabezal;
    }

    /**
     * Define el valor de la propiedad sdtpedidoppggcabezal.
     * 
     * @param value
     *     allowed object is
     *     {@link SDTPedidoPpggCabezal }
     *     
     */
    public void setSdtpedidoppggcabezal(SDTPedidoPpggCabezal value) {
        this.sdtpedidoppggcabezal = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtpedidoppggrenglones.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon }
     *     
     */
    public ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon getSdtpedidoppggrenglones() {
        return sdtpedidoppggrenglones;
    }

    /**
     * Define el valor de la propiedad sdtpedidoppggrenglones.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon }
     *     
     */
    public void setSdtpedidoppggrenglones(ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon value) {
        this.sdtpedidoppggrenglones = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtpedidoppggrespuesta.
     * 
     * @return
     *     possible object is
     *     {@link SdtPedidoPpggRespuesta }
     *     
     */
    public SdtPedidoPpggRespuesta getSdtpedidoppggrespuesta() {
        return sdtpedidoppggrespuesta;
    }

    /**
     * Define el valor de la propiedad sdtpedidoppggrespuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link SdtPedidoPpggRespuesta }
     *     
     */
    public void setSdtpedidoppggrespuesta(SdtPedidoPpggRespuesta value) {
        this.sdtpedidoppggrespuesta = value;
    }

}
