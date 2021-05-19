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
 * <p>Clase Java para SDTArticulosWebxGrupo.Articulo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SDTArticulosWebxGrupo.Articulo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ArtId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="GrupoId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="GrupoNombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtDescripCatalogo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtMedida" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtEspecificaciones" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FamiliaId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="FamiliaDescrip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SubFamiliaId" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="SubFamiliaDescrip" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CantidadPorCaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PrecioPesos" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="PrecioDolares" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Futuro" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Nuevo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Oferta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SdtArtFotos" type="{PpGg}ArrayOfSdtArtFotos.SdtArtFoto"/&gt;
 *         &lt;element name="SdtCategorias" type="{PpGg}ArrayOfSdtCategorias.SdtCategoria"/&gt;
 *         &lt;element name="StockActual" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ArtCantUnidades" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="ArtPreUniPesos" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ArtPreUniDolares" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="MarcaId" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="ArtMostrarEnWeb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtVendibleMercadoLibre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtCodPro" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtNombreML" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtDescripML" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MedidaEmpaque" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Capacidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Talle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ColorDisenio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Edades" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Peso" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ArtCodBar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtCodDun" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ArtCajaSeparada" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SDTArticulosWebxGrupo.Articulo", propOrder = {
    "artId",
    "grupoId",
    "grupoNombre",
    "artDescripCatalogo",
    "artMedida",
    "artEspecificaciones",
    "familiaId",
    "familiaDescrip",
    "subFamiliaId",
    "subFamiliaDescrip",
    "cantidadPorCaja",
    "precioPesos",
    "precioDolares",
    "futuro",
    "nuevo",
    "oferta",
    "sdtArtFotos",
    "sdtCategorias",
    "stockActual",
    "artCantUnidades",
    "artPreUniPesos",
    "artPreUniDolares",
    "marcaId",
    "artMostrarEnWeb",
    "artVendibleMercadoLibre",
    "artCodPro",
    "artNombreML",
    "artDescripML",
    "medidaEmpaque",
    "capacidad",
    "talle",
    "colorDisenio",
    "edades",
    "peso",
    "artCodBar",
    "artCodDun",
    "artCajaSeparada"
})
public class SDTArticulosWebxGrupoArticulo {

    @XmlElement(name = "ArtId", required = true)
    protected String artId;
    @XmlElement(name = "GrupoId")
    protected short grupoId;
    @XmlElement(name = "GrupoNombre", required = true)
    protected String grupoNombre;
    @XmlElement(name = "ArtDescripCatalogo", required = true)
    protected String artDescripCatalogo;
    @XmlElement(name = "ArtMedida", required = true)
    protected String artMedida;
    @XmlElement(name = "ArtEspecificaciones", required = true)
    protected String artEspecificaciones;
    @XmlElement(name = "FamiliaId")
    protected short familiaId;
    @XmlElement(name = "FamiliaDescrip", required = true)
    protected String familiaDescrip;
    @XmlElement(name = "SubFamiliaId")
    protected short subFamiliaId;
    @XmlElement(name = "SubFamiliaDescrip", required = true)
    protected String subFamiliaDescrip;
    @XmlElement(name = "CantidadPorCaja", required = true)
    protected String cantidadPorCaja;
    @XmlElement(name = "PrecioPesos")
    protected double precioPesos;
    @XmlElement(name = "PrecioDolares")
    protected double precioDolares;
    @XmlElement(name = "Futuro", required = true)
    protected String futuro;
    @XmlElement(name = "Nuevo", required = true)
    protected String nuevo;
    @XmlElement(name = "Oferta", required = true)
    protected String oferta;
    @XmlElement(name = "SdtArtFotos", required = true)
    protected ArrayOfSdtArtFotosSdtArtFoto sdtArtFotos;
    @XmlElement(name = "SdtCategorias", required = true)
    protected ArrayOfSdtCategoriasSdtCategoria sdtCategorias;
    @XmlElement(name = "StockActual")
    protected long stockActual;
    @XmlElement(name = "ArtCantUnidades")
    protected short artCantUnidades;
    @XmlElement(name = "ArtPreUniPesos")
    protected double artPreUniPesos;
    @XmlElement(name = "ArtPreUniDolares")
    protected double artPreUniDolares;
    @XmlElement(name = "MarcaId")
    protected byte marcaId;
    @XmlElement(name = "ArtMostrarEnWeb", required = true)
    protected String artMostrarEnWeb;
    @XmlElement(name = "ArtVendibleMercadoLibre", required = true)
    protected String artVendibleMercadoLibre;
    @XmlElement(name = "ArtCodPro", required = true)
    protected String artCodPro;
    @XmlElement(name = "ArtNombreML", required = true)
    protected String artNombreML;
    @XmlElement(name = "ArtDescripML", required = true)
    protected String artDescripML;
    @XmlElement(name = "MedidaEmpaque", required = true)
    protected String medidaEmpaque;
    @XmlElement(name = "Capacidad", required = true)
    protected String capacidad;
    @XmlElement(name = "Talle", required = true)
    protected String talle;
    @XmlElement(name = "ColorDisenio", required = true)
    protected String colorDisenio;
    @XmlElement(name = "Edades", required = true)
    protected String edades;
    @XmlElement(name = "Peso")
    protected int peso;
    @XmlElement(name = "ArtCodBar", required = true)
    protected String artCodBar;
    @XmlElement(name = "ArtCodDun", required = true)
    protected String artCodDun;
    @XmlElement(name = "ArtCajaSeparada", required = true)
    protected String artCajaSeparada;

    /**
     * Obtiene el valor de la propiedad artId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtId() {
        return artId;
    }

    /**
     * Define el valor de la propiedad artId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtId(String value) {
        this.artId = value;
    }

    /**
     * Obtiene el valor de la propiedad grupoId.
     * 
     */
    public short getGrupoId() {
        return grupoId;
    }

    /**
     * Define el valor de la propiedad grupoId.
     * 
     */
    public void setGrupoId(short value) {
        this.grupoId = value;
    }

    /**
     * Obtiene el valor de la propiedad grupoNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrupoNombre() {
        return grupoNombre;
    }

    /**
     * Define el valor de la propiedad grupoNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrupoNombre(String value) {
        this.grupoNombre = value;
    }

    /**
     * Obtiene el valor de la propiedad artDescripCatalogo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtDescripCatalogo() {
        return artDescripCatalogo;
    }

    /**
     * Define el valor de la propiedad artDescripCatalogo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtDescripCatalogo(String value) {
        this.artDescripCatalogo = value;
    }

    /**
     * Obtiene el valor de la propiedad artMedida.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtMedida() {
        return artMedida;
    }

    /**
     * Define el valor de la propiedad artMedida.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtMedida(String value) {
        this.artMedida = value;
    }

    /**
     * Obtiene el valor de la propiedad artEspecificaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtEspecificaciones() {
        return artEspecificaciones;
    }

    /**
     * Define el valor de la propiedad artEspecificaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtEspecificaciones(String value) {
        this.artEspecificaciones = value;
    }

    /**
     * Obtiene el valor de la propiedad familiaId.
     * 
     */
    public short getFamiliaId() {
        return familiaId;
    }

    /**
     * Define el valor de la propiedad familiaId.
     * 
     */
    public void setFamiliaId(short value) {
        this.familiaId = value;
    }

    /**
     * Obtiene el valor de la propiedad familiaDescrip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamiliaDescrip() {
        return familiaDescrip;
    }

    /**
     * Define el valor de la propiedad familiaDescrip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamiliaDescrip(String value) {
        this.familiaDescrip = value;
    }

    /**
     * Obtiene el valor de la propiedad subFamiliaId.
     * 
     */
    public short getSubFamiliaId() {
        return subFamiliaId;
    }

    /**
     * Define el valor de la propiedad subFamiliaId.
     * 
     */
    public void setSubFamiliaId(short value) {
        this.subFamiliaId = value;
    }

    /**
     * Obtiene el valor de la propiedad subFamiliaDescrip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubFamiliaDescrip() {
        return subFamiliaDescrip;
    }

    /**
     * Define el valor de la propiedad subFamiliaDescrip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubFamiliaDescrip(String value) {
        this.subFamiliaDescrip = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadPorCaja.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCantidadPorCaja() {
        return cantidadPorCaja;
    }

    /**
     * Define el valor de la propiedad cantidadPorCaja.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCantidadPorCaja(String value) {
        this.cantidadPorCaja = value;
    }

    /**
     * Obtiene el valor de la propiedad precioPesos.
     * 
     */
    public double getPrecioPesos() {
        return precioPesos;
    }

    /**
     * Define el valor de la propiedad precioPesos.
     * 
     */
    public void setPrecioPesos(double value) {
        this.precioPesos = value;
    }

    /**
     * Obtiene el valor de la propiedad precioDolares.
     * 
     */
    public double getPrecioDolares() {
        return precioDolares;
    }

    /**
     * Define el valor de la propiedad precioDolares.
     * 
     */
    public void setPrecioDolares(double value) {
        this.precioDolares = value;
    }

    /**
     * Obtiene el valor de la propiedad futuro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuturo() {
        return futuro;
    }

    /**
     * Define el valor de la propiedad futuro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuturo(String value) {
        this.futuro = value;
    }

    /**
     * Obtiene el valor de la propiedad nuevo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuevo() {
        return nuevo;
    }

    /**
     * Define el valor de la propiedad nuevo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuevo(String value) {
        this.nuevo = value;
    }

    /**
     * Obtiene el valor de la propiedad oferta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOferta() {
        return oferta;
    }

    /**
     * Define el valor de la propiedad oferta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOferta(String value) {
        this.oferta = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtArtFotos.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtArtFotosSdtArtFoto }
     *     
     */
    public ArrayOfSdtArtFotosSdtArtFoto getSdtArtFotos() {
        return sdtArtFotos;
    }

    /**
     * Define el valor de la propiedad sdtArtFotos.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtArtFotosSdtArtFoto }
     *     
     */
    public void setSdtArtFotos(ArrayOfSdtArtFotosSdtArtFoto value) {
        this.sdtArtFotos = value;
    }

    /**
     * Obtiene el valor de la propiedad sdtCategorias.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSdtCategoriasSdtCategoria }
     *     
     */
    public ArrayOfSdtCategoriasSdtCategoria getSdtCategorias() {
        return sdtCategorias;
    }

    /**
     * Define el valor de la propiedad sdtCategorias.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSdtCategoriasSdtCategoria }
     *     
     */
    public void setSdtCategorias(ArrayOfSdtCategoriasSdtCategoria value) {
        this.sdtCategorias = value;
    }

    /**
     * Obtiene el valor de la propiedad stockActual.
     * 
     */
    public long getStockActual() {
        return stockActual;
    }

    /**
     * Define el valor de la propiedad stockActual.
     * 
     */
    public void setStockActual(long value) {
        this.stockActual = value;
    }

    /**
     * Obtiene el valor de la propiedad artCantUnidades.
     * 
     */
    public short getArtCantUnidades() {
        return artCantUnidades;
    }

    /**
     * Define el valor de la propiedad artCantUnidades.
     * 
     */
    public void setArtCantUnidades(short value) {
        this.artCantUnidades = value;
    }

    /**
     * Obtiene el valor de la propiedad artPreUniPesos.
     * 
     */
    public double getArtPreUniPesos() {
        return artPreUniPesos;
    }

    /**
     * Define el valor de la propiedad artPreUniPesos.
     * 
     */
    public void setArtPreUniPesos(double value) {
        this.artPreUniPesos = value;
    }

    /**
     * Obtiene el valor de la propiedad artPreUniDolares.
     * 
     */
    public double getArtPreUniDolares() {
        return artPreUniDolares;
    }

    /**
     * Define el valor de la propiedad artPreUniDolares.
     * 
     */
    public void setArtPreUniDolares(double value) {
        this.artPreUniDolares = value;
    }

    /**
     * Obtiene el valor de la propiedad marcaId.
     * 
     */
    public byte getMarcaId() {
        return marcaId;
    }

    /**
     * Define el valor de la propiedad marcaId.
     * 
     */
    public void setMarcaId(byte value) {
        this.marcaId = value;
    }

    /**
     * Obtiene el valor de la propiedad artMostrarEnWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtMostrarEnWeb() {
        return artMostrarEnWeb;
    }

    /**
     * Define el valor de la propiedad artMostrarEnWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtMostrarEnWeb(String value) {
        this.artMostrarEnWeb = value;
    }

    /**
     * Obtiene el valor de la propiedad artVendibleMercadoLibre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtVendibleMercadoLibre() {
        return artVendibleMercadoLibre;
    }

    /**
     * Define el valor de la propiedad artVendibleMercadoLibre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtVendibleMercadoLibre(String value) {
        this.artVendibleMercadoLibre = value;
    }

    /**
     * Obtiene el valor de la propiedad artCodPro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtCodPro() {
        return artCodPro;
    }

    /**
     * Define el valor de la propiedad artCodPro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtCodPro(String value) {
        this.artCodPro = value;
    }

    /**
     * Obtiene el valor de la propiedad artNombreML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtNombreML() {
        return artNombreML;
    }

    /**
     * Define el valor de la propiedad artNombreML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtNombreML(String value) {
        this.artNombreML = value;
    }

    /**
     * Obtiene el valor de la propiedad artDescripML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtDescripML() {
        return artDescripML;
    }

    /**
     * Define el valor de la propiedad artDescripML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtDescripML(String value) {
        this.artDescripML = value;
    }

    /**
     * Obtiene el valor de la propiedad medidaEmpaque.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedidaEmpaque() {
        return medidaEmpaque;
    }

    /**
     * Define el valor de la propiedad medidaEmpaque.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedidaEmpaque(String value) {
        this.medidaEmpaque = value;
    }

    /**
     * Obtiene el valor de la propiedad capacidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacidad() {
        return capacidad;
    }

    /**
     * Define el valor de la propiedad capacidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacidad(String value) {
        this.capacidad = value;
    }

    /**
     * Obtiene el valor de la propiedad talle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalle() {
        return talle;
    }

    /**
     * Define el valor de la propiedad talle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalle(String value) {
        this.talle = value;
    }

    /**
     * Obtiene el valor de la propiedad colorDisenio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorDisenio() {
        return colorDisenio;
    }

    /**
     * Define el valor de la propiedad colorDisenio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorDisenio(String value) {
        this.colorDisenio = value;
    }

    /**
     * Obtiene el valor de la propiedad edades.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdades() {
        return edades;
    }

    /**
     * Define el valor de la propiedad edades.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdades(String value) {
        this.edades = value;
    }

    /**
     * Obtiene el valor de la propiedad peso.
     * 
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Define el valor de la propiedad peso.
     * 
     */
    public void setPeso(int value) {
        this.peso = value;
    }

    /**
     * Obtiene el valor de la propiedad artCodBar.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtCodBar() {
        return artCodBar;
    }

    /**
     * Define el valor de la propiedad artCodBar.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtCodBar(String value) {
        this.artCodBar = value;
    }

    /**
     * Obtiene el valor de la propiedad artCodDun.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtCodDun() {
        return artCodDun;
    }

    /**
     * Define el valor de la propiedad artCodDun.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtCodDun(String value) {
        this.artCodDun = value;
    }

    /**
     * Obtiene el valor de la propiedad artCajaSeparada.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtCajaSeparada() {
        return artCajaSeparada;
    }

    /**
     * Define el valor de la propiedad artCajaSeparada.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtCajaSeparada(String value) {
        this.artCajaSeparada = value;
    }

}
