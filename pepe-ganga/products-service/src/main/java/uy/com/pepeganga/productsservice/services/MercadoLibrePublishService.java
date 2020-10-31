package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.Image;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.productsservice.gridmodels.DetailsPublicationsMeliGrid;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;

public interface MercadoLibrePublishService {
	
	MarketplaceDetails getDetailsMarketplaces(Integer idUser);
	
	SelectedProducResponse storeProductToPublish(String idProfile, Short marketplace, List<String> product);
		
	PageItemMeliGrid getItemsMeliByFiltersAndPaginator(String profileEncode, String sku, String nameProduct, Short state, Short familyId,
			double minPrice, double maxPrice, Pageable pageable);

	ReasonResponse storeCommonData(String profileEncode, String description,  List<String> skuList, List<Image> images);
	
	EditableProductModel editInfoOfProduct(EditableProductModel product, List<Integer>imagesToDelete )  throws Exception;
	
	EditableProductModel getCustomProduct(Integer id);

	List<MercadoLibrePublications> getFullProduct(List<String> skus, String profileEncode) throws Exception;

	List<EditableProductModel> getFullProductById(List<Integer> ids) throws Exception;

	List<DetailsPublicationsMeliGrid> getPublicationsDetailsBySellerProfile(Integer profileId, int page, int size);
}

