package uy.com.pepeganga.productsservice.services;

import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;

import java.util.List;

public interface MarketplaceService {


    List<Marketplace> getMarketplaces();

    Marketplace createMarketplace(Marketplace marketplace);

    Marketplace updateMarketplace(Marketplace marketplace, Short id);

    void deleteMarketplace(Short id);
    
    public List<MarketplaceDetails> getListDetailMarketplacesByUser(Integer idUser);  
    
}
