package uy.com.pepeganga.consumingwsstore.conversions;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uy.com.pepeganga.consumingwsstore.models.ItemModelResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebParcialArticulo;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SdtArtFotosSdtArtFoto;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SdtCategoriasSdtCategoria;


public class ConvertModels {

	@Autowired
	private static ItemModelResponse itemResp;	
	
	public static ItemModelResponse convetToItemModel(SDTArticulosWebParcialArticulo artResp) {
		itemResp = null;		
		
		if (!isNull(artResp))			
		 {
			itemResp = new ItemModelResponse();
			itemResp.setSku(artResp.getId());
			itemResp.setArtDescripCatalogo(artResp.getArtDescripCatalogo());
			itemResp.setArtMedida(artResp.getArtMedida());
			itemResp.setArtEspecificaciones(artResp.getArtEspecificaciones());
			itemResp.setFamiliaId(artResp.getFamiliaId());
			itemResp.setFamiliaDescrip(artResp.getFamiliaDescrip());
			itemResp.setSubFamiliaId(artResp.getSubFamiliaId());
			itemResp.setSubFamiliaDescrip(artResp.getSubFamiliaDescrip());
			itemResp.setCantidadPorCaja(artResp.getCantidadPorCaja());
			itemResp.setPrecioPesos(artResp.getPrecioPesos());
			itemResp.setPrecioDolares(artResp.getPrecioDolares());
			itemResp.setFuturo(artResp.getFuturo());
			itemResp.setNuevo(artResp.getNuevo());
			itemResp.setOferta(artResp.getOferta());

			for (SdtArtFotosSdtArtFoto variable : artResp.getSdtArtFotos().getSdtArtFotosSdtArtFoto())
				itemResp.getArtFotosList().add(variable.getFoto());

			for (SdtCategoriasSdtCategoria variable : artResp.getSdtCategorias().getSdtCategoriasSdtCategoria())
				itemResp.getCategoriasMap().put(Short.toString(variable.getCategoriaId()),
						variable.getCategoriaDescrip());

			itemResp.setStockActual(artResp.getStockActual());
			itemResp.setArtCantUnidades(artResp.getArtCantUnidades());
			itemResp.setArtPreUniPesos(artResp.getArtPreUniPesos());
			itemResp.setArtPreUniDolares(artResp.getArtPreUniDolares());
			itemResp.setMarcaId(artResp.getMarcaId());
			itemResp.setArtMostrarEnWeb(artResp.getArtMostrarEnWeb());
			itemResp.setArtVendibleMercadoLibre(artResp.getArtVendibleMercadoLibre());
			itemResp.setArtCodPro(artResp.getArtCodPro());
			itemResp.setArtNombreML(artResp.getArtNombreML());
			itemResp.setArtDescripML(artResp.getArtDescripML());
			itemResp.setMedidaEmpaque(artResp.getMedidaEmpaque());
			itemResp.setCapacidad(artResp.getCapacidad());
			itemResp.setTalle(artResp.getTalle());
		}
		return itemResp;
	}

	public static List<ItemModelResponse> convetToItemModelList(List<SDTArticulosWebParcialArticulo> artList)
	{
		List<ItemModelResponse> itemList = new ArrayList<ItemModelResponse>();
		
		if(!artList.isEmpty())
		{
			for (SDTArticulosWebParcialArticulo variable : artList)
				itemList.add(convetToItemModel(variable));
		}
		return itemList;
	}
}
