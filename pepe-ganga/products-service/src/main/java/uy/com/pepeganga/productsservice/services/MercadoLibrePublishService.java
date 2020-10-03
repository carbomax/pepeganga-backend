package uy.com.pepeganga.productsservice.services;

import java.util.List;

import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;

public interface MercadoLibrePublishService {
	
	MarketplaceDetails getDetailsMarketplaces(Integer idUser);
	
	SelectedProducResponse storeProductToPublish(Integer idUser, MarketplaceType marketplace, List<String> product);
		
	
	
}
