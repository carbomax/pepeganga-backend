package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import uy.com.pepeganga.business.common.models.Image;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;

public interface MercadoLibrePublishService {
	
	MarketplaceDetails getDetailsMarketplaces(Integer idUser);
	
	SelectedProducResponse storeProductToPublish(Integer idProfile, Short marketplace, List<String> product);
		
	PageItemMeliGrid getItemsMeliByFiltersAndPaginator(String sku, String nameProduct, Short state, Short familyId,
			double minPrice, double maxPrice, Pageable pageable);

	ReasonResponse storeCommonData(Integer idProfile, String description,  List<String> skuList, List<Image> images);
	
	ReasonResponse editInfoOfProduct(EditableProductModel product );
}

