package uy.com.pepeganga.productsservice.services;

import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.productsservice.repository.MarketplaceRepository;

import java.util.List;

@Service
public class MarketplaceServiceImpl implements MarketplaceService{

    final
    MarketplaceRepository marketplaceRepository;

    public MarketplaceServiceImpl(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }

    @Override
    public List<Marketplace> getMarketplaces() {
        return (List<Marketplace>) marketplaceRepository.findAll();
    }
}
