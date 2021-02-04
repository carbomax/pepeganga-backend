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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para SDTPedidoPpggCabezal complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SDTPedidoPpggCabezal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Usuario" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="IdPedido" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ClienteId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="ClienteNombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Localidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Departamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RUT" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Cedula" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Moneda" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="Observaciones" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SDTPedidoPpggCabezal", propOrder = {
    "usuario",
    "idPedido",
    "fecha",
    "clienteId",
    "clienteNombre",
    "eMail",
    "direccion",
    "localidad",
    "departamento",
    "rut",
    "cedula",
    "moneda",
    "observaciones"
})
public class SDTPedidoPpggCabezal {

    @XmlElement(name = "Usuario")
    protected short usuario;
    @XmlElement(name = "IdPedido")
    protected int idPedido;
    @XmlElement(name = "Fecha", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(name = "ClienteId")
    protected short clienteId;
    @XmlElement(name = "ClienteNombre", required = true)
    protected String clienteNombre;
    @XmlElement(name = "EMail", required = true)
    protected String eMail;
    @XmlElement(name = "Direccion", required = true)
    protected String direccion;
    @XmlElement(name = "Localidad", required = true)
    protected String localidad;
    @XmlElement(name = "Departamento", required = true)
    protected String departamento;
    @XmlElement(name = "RUT")
    protected long rut;
    @XmlElement(name = "Cedula")
    protected long cedula;
    @XmlElement(name = "Moneda")
    protected byte moneda;
    @XmlElement(name = "Observaciones", required = true)
    protected String observaciones;

    /**
     * Obtiene el valor de la propiedad usuario.
     * 
     */
    public short getUsuario() {
        return usuario;
    }

    /**
     * Define el valor de la propiedad usuario.
     * 
     */
    public void setUsuario(short value) {
        this.usuario = value;
    }

    /**
     * Obtiene el valor de la propiedad idPedido.
     * 
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Define el valor de la propiedad idPedido.
     * 
     */
    public void setIdPedido(int value) {
        this.idPedido = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad clienteId.
     * 
     */
    public short getClienteId() {
        return clienteId;
    }

    /**
     * Define el valor de la propiedad clienteId.
     * 
     */
    public void setClienteId(short value) {
        this.clienteId = value;
    }

    /**
     * Obtiene el valor de la propiedad clienteNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClienteNombre() {
        return clienteNombre;
    }

    /**
     * Define el valor de la propiedad clienteNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClienteNombre(String value) {
        this.clienteNombre = value;
    }

    /**
     * Obtiene el valor de la propiedad eMail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Define el valor de la propiedad eMail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad localidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Define el valor de la propiedad localidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalidad(String value) {
        this.localidad = value;
    }

    /**
     * Obtiene el valor de la propiedad departamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define el valor de la propiedad departamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartamento(String value) {
        this.departamento = value;
    }

    /**
     * Obtiene el valor de la propiedad rut.
     * 
     */
    public long getRUT() {
        return rut;
    }

    /**
     * Define el valor de la propiedad rut.
     * 
     */
    public void setRUT(long value) {
        this.rut = value;
    }

    /**
     * Obtiene el valor de la propiedad cedula.
     * 
     */
    public long getCedula() {
        return cedula;
    }

    /**
     * Define el valor de la propiedad cedula.
     * 
     */
    public void setCedula(long value) {
        this.cedula = value;
    }

    /**
     * Obtiene el valor de la propiedad moneda.
     * 
     */
    public byte getMoneda() {
        return moneda;
    }

    /**
     * Define el valor de la propiedad moneda.
     * 
     */
    public void setMoneda(byte value) {
        this.moneda = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

}
