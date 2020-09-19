package uy.com.pepeganga.consumingwsstore.conversions;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uy.com.pepeganga.consumingwsstore.entities.Brand;
import uy.com.pepeganga.consumingwsstore.entities.Category;
import uy.com.pepeganga.consumingwsstore.entities.Family;
import uy.com.pepeganga.consumingwsstore.entities.Image;
import uy.com.pepeganga.consumingwsstore.entities.Item;
import uy.com.pepeganga.consumingwsstore.entities.SubFamily;
import uy.com.pepeganga.consumingwsstore.gridmodels.ItemGrid;
import uy.com.pepeganga.consumingwsstore.wsdl.families.SdtLineasSubFliasSdtLineaSubFlias;
import uy.com.pepeganga.consumingwsstore.wsdl.families.SdtSubFliasSdtSubFlia;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebParcialArticulo;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SdtArtFotosSdtArtFoto;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SdtCategoriasSdtCategoria;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.SDTMarcasSDTMarca;

public class ConvertModels {

	@Autowired
	private static Item itemResp;	
	
	@Autowired
	private static Category categoryResp;	
	
	@Autowired
	private static Family familyResp;
	
	@Autowired
	private static SubFamily subfamilyResp;	
	
	@Autowired
	private static Brand brandResp;
	
	@Autowired
	private static ItemGrid itemGridResp;
	
	
	public static Item convetToItemEntity(SDTArticulosWebParcialArticulo artResp) {
		itemResp = null;		
		
		if (!isNull(artResp))			
		 {
			itemResp = new Item();
			itemResp.setSku(artResp.getId());
			itemResp.setArtDescripCatalogo(artResp.getArtDescripCatalogo());
			itemResp.setArtMedida(artResp.getArtMedida());
			itemResp.setArtEspecificaciones(artResp.getArtEspecificaciones());			
			itemResp.setCantidadPorCaja(artResp.getCantidadPorCaja());
			itemResp.setPrecioPesos(artResp.getPrecioPesos());
			itemResp.setPrecioDolares(artResp.getPrecioDolares());
			itemResp.setFuturo(artResp.getFuturo());
			itemResp.setNuevo(artResp.getNuevo());
			itemResp.setOferta(artResp.getOferta());
			itemResp.setStockActual(artResp.getStockActual());
			itemResp.setArtCantUnidades(artResp.getArtCantUnidades());
			itemResp.setArtPreUniPesos(artResp.getArtPreUniPesos());
			itemResp.setArtPreUniDolares(artResp.getArtPreUniDolares());		
			itemResp.setArtMostrarEnWeb(artResp.getArtMostrarEnWeb());
			itemResp.setArtVendibleMercadoLibre(artResp.getArtVendibleMercadoLibre());
			itemResp.setArtCodPro(artResp.getArtCodPro());
			itemResp.setArtNombreML(artResp.getArtNombreML());
			itemResp.setArtDescripML(artResp.getArtDescripML());
			itemResp.setMedidaEmpaque(artResp.getMedidaEmpaque());
			itemResp.setCapacidad(artResp.getCapacidad());
			itemResp.setTalle(artResp.getTalle());			
			
			itemResp.setFamily(createFamilyInstance(artResp.getFamiliaId()));			
			itemResp.setBrand(createBrandInstance(artResp.getMarcaId()));
			
			for (SdtCategoriasSdtCategoria variable : artResp.getSdtCategorias().getSdtCategoriasSdtCategoria())
				if(variable != null)
					itemResp.getCategories().add(convetToCategoryEntity(variable));	
			
			for (SdtArtFotosSdtArtFoto variable : artResp.getSdtArtFotos().getSdtArtFotosSdtArtFoto())
				itemResp.getImage().add(createImageInstanceWithoutId(variable.getFoto()));
			
		}
		return itemResp;
	}

	public static List<Item> convetToItemEntityList(List<SDTArticulosWebParcialArticulo> artList)
	{
		List<Item> itemList = new ArrayList<Item>();
		
		if(!artList.isEmpty())
		{
			for (SDTArticulosWebParcialArticulo variable : artList) {
				if(variable != null)
					itemList.add(convetToItemEntity(variable));
			}
		}
		return itemList;
	}
	
	public static ItemGrid convetToItemGrid(SDTArticulosWebParcialArticulo artResp) {
		itemGridResp = null;		
		
		if (!isNull(artResp))			
		 {
			itemGridResp = new ItemGrid();
			itemGridResp.setSku(artResp.getId());
			itemGridResp.setPriceUYU(artResp.getPrecioPesos());
			itemGridResp.setPriceUSD(artResp.getPrecioDolares());			
			itemGridResp.setName(artResp.getArtDescripCatalogo());
			itemGridResp.setImage(artResp.getSdtArtFotos().getSdtArtFotosSdtArtFoto().get(0).getFoto());		

			for (SdtCategoriasSdtCategoria variable : artResp.getSdtCategorias().getSdtCategoriasSdtCategoria())
				itemGridResp.getCategories().put(Short.toString(variable.getCategoriaId()),
						variable.getCategoriaDescrip());			
		}
		return itemGridResp;
	}
	
	public static List<ItemGrid> convetToItemGridList(List<SDTArticulosWebParcialArticulo> artList)
	{
		List<ItemGrid> itemGridList = new ArrayList<ItemGrid>();
		
		if(!artList.isEmpty())
		{
			for (SDTArticulosWebParcialArticulo variable : artList)
				itemGridList.add(convetToItemGrid(variable));
		}
		return itemGridList;
	}
		
	public static Category convetToCategoryEntity(uy.com.pepeganga.consumingwsstore.wsdl.categories.SdtCategoriasSdtCategoria category) {
		
		categoryResp = null;		
		
		if (!isNull(category))			
		 {
			categoryResp = new Category();
			categoryResp.setId(category.getCategoriaId());
			categoryResp.setDescription(category.getCategoriaDescrip());
		 }
		 return categoryResp;
	}
	
	public static Category convetToCategoryEntity(SdtCategoriasSdtCategoria category) {
		
		categoryResp = null;		
		
		if (!isNull(category))			
		 {
			categoryResp = new Category();
			categoryResp.setId(category.getCategoriaId());
			categoryResp.setDescription(category.getCategoriaDescrip());
		 }
		 return categoryResp;
	}
	
	public static List<Category> convetToCategoryEntityList(List<uy.com.pepeganga.consumingwsstore.wsdl.categories.SdtCategoriasSdtCategoria> catList){
		
		List<Category> categoryList = new ArrayList<Category>();
		
		if(!catList.isEmpty())
		{
			for (uy.com.pepeganga.consumingwsstore.wsdl.categories.SdtCategoriasSdtCategoria variable : catList)
				if(variable != null)
					categoryList.add(convetToCategoryEntity(variable));
		}
		return categoryList;
	}
	
	public static Family convetToFamilyEntity(SdtLineasSubFliasSdtLineaSubFlias family) {
		
		familyResp = null;		
		
		if (!isNull(family))			
		 {
			familyResp = new Family();
			familyResp.setId(family.getLinId());
			familyResp.setDescription(family.getLinDsc());
			if(family.getSdtSubFlias() != null)
				familyResp.setSubfamilies(convetToSubFamilyEntityList(family.getSdtSubFlias().getSdtSubFliasSdtSubFlia()));	
		}
		return familyResp;
	}
	
	public static List<Family> convetToFamilyEntityList(List<SdtLineasSubFliasSdtLineaSubFlias> famiList){
		
		List<Family> familyList = new ArrayList<Family>();
		
		if(!famiList.isEmpty())
		{
			for (SdtLineasSubFliasSdtLineaSubFlias variable : famiList)
				if(variable != null)
					familyList.add(convetToFamilyEntity(variable));
		}
		return familyList;
	}	
	
	public static SubFamily convetToSubFamilyEntity(SdtSubFliasSdtSubFlia subfamily) {
		
		subfamilyResp = null;		
		
		if (!isNull(subfamily))			
		 {
			subfamilyResp = new SubFamily();
			subfamilyResp.setId(subfamily.getSubFliaId());
			subfamilyResp.setDescription(subfamily.getSubFliaDsc());
		 }
		return subfamilyResp;
	}
	
	public static List<SubFamily> convetToSubFamilyEntityList(List<SdtSubFliasSdtSubFlia> subfamiList){
		
		List<SubFamily> familyList = new ArrayList<SubFamily>();
		
		if(!subfamiList.isEmpty())
		{
			for (SdtSubFliasSdtSubFlia variable : subfamiList)
				if(variable != null)
					familyList.add(convetToSubFamilyEntity(variable));
		}
		return familyList;
	}
	
	public static Brand convertToBrandEntity(SDTMarcasSDTMarca brand) {
	
		brandResp = null;		
		
		if (!isNull(brand))			
		 {
			brandResp = new Brand();
			brandResp.setId(brand.getMarcaId());
			brandResp.setDescription(brand.getMarcaDescrip());
			brandResp.setMarcaInUse(brand.getMarcaEnUso());
		 }
		 return brandResp;
	}
	
	public static List<Brand> convetToBrandEntityList(List<SDTMarcasSDTMarca> brandList){
		
		List<Brand> responseList = new ArrayList<Brand>();
		
		if(!brandList.isEmpty())
		{
			for (SDTMarcasSDTMarca variable : brandList)
				if(variable != null)
					responseList.add(convertToBrandEntity(variable));
		}
		return responseList;
	}
	
	/*Auxiliar Class*/
	//Nota: cambiar todos los metodos despues a private cuando quite la generacion ficticia de Item en el Run()
 	public final static Family createFamilyInstance(short id) {
		Family family = new Family();
		family.setId(id);
		return family;
	}
	
	public final static Brand createBrandInstance(short id) {
		Brand brand = new Brand();
		brand.setId(id);
		return brand;
	}
	
	public final static Image createImageInstanceWithoutId(String photo) {
		Image image = new Image();
		image.setPhoto(photo);
		return image;
	}
}
