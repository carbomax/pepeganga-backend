package uy.com.pepeganga.consumingwsstore.models;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ItemModelResponse {

	 
	private String sku;
	  
	private String artDescripCatalogo;
   
	private String artMedida;  
    
	private String artEspecificaciones;
  
	private short familiaId;

	private String familiaDescrip;
  
	private short subFamiliaId;
 
	private String subFamiliaDescrip;
  
	private String cantidadPorCaja;
   
	private double precioPesos;
  
	private double precioDolares;
 
	private String futuro;
 
	private String nuevo;
  
	private String oferta;
   
	private List<String> artFotosList;
    
	private Map<String, String> categoriasMap;

	private long stockActual;
  
	private short artCantUnidades;
 
	private double artPreUniPesos;

	private double artPreUniDolares;
   
	private byte marcaId;
    
	private String artMostrarEnWeb;
  
	private String artVendibleMercadoLibre;
    
	private String artCodPro;
 
	private String artNombreML;
   
	private String artDescripML;
    
	private String medidaEmpaque;
    
	private String capacidad;
   
	private String talle;
   
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

	public short getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(short familiaId) {
		this.familiaId = familiaId;
	}

	public String getFamiliaDescrip() {
		return familiaDescrip;
	}

	public void setFamiliaDescrip(String familiaDescrip) {
		this.familiaDescrip = familiaDescrip;
	}

	public short getSubFamiliaId() {
		return subFamiliaId;
	}

	public void setSubFamiliaId(short subFamiliaId) {
		this.subFamiliaId = subFamiliaId;
	}

	public String getSubFamiliaDescrip() {
		return subFamiliaDescrip;
	}

	public void setSubFamiliaDescrip(String subFamiliaDescrip) {
		this.subFamiliaDescrip = subFamiliaDescrip;
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

	public List<String> getArtFotosList() {
		return artFotosList;
	}

	public void setArtFotosList(List<String> artFotosList) {
		this.artFotosList = artFotosList;
	}

	public Map<String, String> getCategoriasMap() {
		return categoriasMap;
	}

	public void setCategoriasMap(Map<String, String> categoriasMap) {
		this.categoriasMap = categoriasMap;
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

	public byte getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(byte marcaId) {
		this.marcaId = marcaId;
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
	
}
