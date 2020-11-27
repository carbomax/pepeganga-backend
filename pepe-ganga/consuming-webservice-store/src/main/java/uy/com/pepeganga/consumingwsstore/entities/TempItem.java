package uy.com.pepeganga.consumingwsstore.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tempitem")
public class TempItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="sku")
    private String sku;

    @Column(name="artDescripCatalogo", columnDefinition = "TEXT")
    private String artDescripCatalogo;

    @Column(name="artMedida")
    private String artMedida;

    @Column(name="artEspecificaciones")
    private String artEspecificaciones;

    @Column(name="cantidadPorCaja")
    private String cantidadPorCaja;

    @Column(name="precioPesos")
    private double precioPesos;

    @Column(name="precioDolares")
    private double precioDolares;

    @Column(name = "images", columnDefinition = "blob", length = 10000)
    private byte[] images;

    @Column(name="futuro")
    private String futuro;

    @Column(name="nuevo")
    private String nuevo;

    @Column(name="oferta")
    private String oferta;

    @Column(name="stockActual")
    private long stockActual;

    @Column(name="artCantUnidades")
    private short artCantUnidades;

    @Column(name="artPreUniPesos")
    private double artPreUniPesos;

    @Column(name="artPreUniDolares")
    private double artPreUniDolares;

    @Column(name="artMostrarEnWeb")
    private String artMostrarEnWeb;

    @Column(name="artVendibleMercadoLibre")
    private String artVendibleMercadoLibre;

    @Column(name="artCodPro")
    private String artCodPro;

    @Column(name="artNombreML")
    private String artNombreML;

    @Column(name="artDescripML", columnDefinition = "TEXT")
    private String artDescripML;

    @Column(name="medidaEmpaque")
    private String medidaEmpaque;

    @Column(name="capacidad")
    private String capacidad;

    @Column(name="talle")
    private String talle;

    @ManyToOne
    @JoinColumn(name = "tempfamily_id")
    private TempFamily family;

    @ManyToOne
    @JoinColumn(name = "tempbrand_id")
    private TempBrand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tempitem_tempcategory")
    private List<TempCategory> categories = new ArrayList<>();

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getArtDescripCatalogo() {
        return artDescripCatalogo;
    }

    public void setArtDescripCatalogo(String artDescripCatalogo) {
        this.artDescripCatalogo = artDescripCatalogo;
    }

    public String getArtMedida() {
        return artMedida;
    }

    public void setArtMedida(String artMedida) {
        this.artMedida = artMedida;
    }

    public String getArtEspecificaciones() {
        return artEspecificaciones;
    }

    public void setArtEspecificaciones(String artEspecificaciones) {
        this.artEspecificaciones = artEspecificaciones;
    }

    public String getCantidadPorCaja() {
        return cantidadPorCaja;
    }

    public void setCantidadPorCaja(String cantidadPorCaja) {
        this.cantidadPorCaja = cantidadPorCaja;
    }

    public double getPrecioPesos() {
        return precioPesos;
    }

    public void setPrecioPesos(double precioPesos) {
        this.precioPesos = precioPesos;
    }

    public double getPrecioDolares() {
        return precioDolares;
    }

    public void setPrecioDolares(double precioDolares) {
        this.precioDolares = precioDolares;
    }

    public String getFuturo() {
        return futuro;
    }

    public void setFuturo(String futuro) {
        this.futuro = futuro;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public long getStockActual() {
        return stockActual;
    }

    public void setStockActual(long stockActual) {
        this.stockActual = stockActual;
    }

    public short getArtCantUnidades() {
        return artCantUnidades;
    }

    public void setArtCantUnidades(short artCantUnidades) {
        this.artCantUnidades = artCantUnidades;
    }

    public double getArtPreUniPesos() {
        return artPreUniPesos;
    }

    public void setArtPreUniPesos(double artPreUniPesos) {
        this.artPreUniPesos = artPreUniPesos;
    }

    public double getArtPreUniDolares() {
        return artPreUniDolares;
    }

    public void setArtPreUniDolares(double artPreUniDolares) {
        this.artPreUniDolares = artPreUniDolares;
    }

    public String getArtMostrarEnWeb() {
        return artMostrarEnWeb;
    }

    public void setArtMostrarEnWeb(String artMostrarEnWeb) {
        this.artMostrarEnWeb = artMostrarEnWeb;
    }

    public String getArtVendibleMercadoLibre() {
        return artVendibleMercadoLibre;
    }

    public void setArtVendibleMercadoLibre(String artVendibleMercadoLibre) {
        this.artVendibleMercadoLibre = artVendibleMercadoLibre;
    }

    public String getArtCodPro() {
        return artCodPro;
    }

    public void setArtCodPro(String artCodPro) {
        this.artCodPro = artCodPro;
    }

    public String getArtNombreML() {
        return artNombreML;
    }

    public void setArtNombreML(String artNombreML) {
        this.artNombreML = artNombreML;
    }

    public String getArtDescripML() {
        return artDescripML;
    }

    public void setArtDescripML(String artDescripML) {
        this.artDescripML = artDescripML;
    }

    public String getMedidaEmpaque() {
        return medidaEmpaque;
    }

    public void setMedidaEmpaque(String medidaEmpaque) {
        this.medidaEmpaque = medidaEmpaque;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getTalle() {
        return talle;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    public TempFamily getFamily() {
        return family;
    }

    public void setFamily(TempFamily family) {
        this.family = family;
    }

    public TempBrand getBrand() {
        return brand;
    }

    public void setBrand(TempBrand brand) {
        this.brand = brand;
    }

    public List<TempCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TempCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempItem)) return false;
        TempItem tempItem = (TempItem) o;
        return Objects.equals(sku, tempItem.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
