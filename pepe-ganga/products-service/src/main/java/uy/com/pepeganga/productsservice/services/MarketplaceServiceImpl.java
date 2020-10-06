package uy.com.pepeganga.productsservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.repository.MarketplaceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketplaceServiceImpl implements MarketplaceService {

	@Autowired
	MercadoLibrePublishService mercadoLibreService;
	
    private final MarketplaceRepository marketplaceRepository;

    public MarketplaceServiceImpl(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }

    @Override
    @Cacheable("marketplaces")
    public List<Marketplace> getMarketplaces() {
        return (List<Marketplace>) marketplaceRepository.findAll();
    }

    @Override
    @CacheEvict(value = "marketplaces", allEntries = true)
    public Marketplace createMarketplace(Marketplace marketplace) {
        if (!marketplaceRepository.existsByName(marketplace.getName())) {
            return marketplaceRepository.save(marketplace);
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Marketplace with name: %s exist", marketplace.getName()));
    }

    @Override
    @CacheEvict(value = "marketplaces", allEntries = true)
    public Marketplace updateMarketplace(Marketplace marketplace, Short id) {
        if (!marketplaceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Marketplace not updated with id %s", id));
        }
        Marketplace marketplaceToUpdate = new Marketplace();
        marketplaceToUpdate.setId(id);
        marketplaceToUpdate.setName(marketplace.getName());
        return marketplaceRepository.save(marketplaceToUpdate);
    }


    @Override
    @CacheEvict(value = "marketplaces", allEntries = true)
    public void deleteMarketplace(Short id) {
        if (!marketplaceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Marketplace not deleted with id %s, it not exist", id));
        } else marketplaceRepository.deleteById(id);

    }
   
    public List<MarketplaceDetails> getListDetailMarketplacesByProfile(Integer idProfile){
    	List<MarketplaceDetails> marketplacesDetailList = new ArrayList<>();
    	marketplacesDetailList.add(mercadoLibreService.getDetailsMarketplaces(idProfile));
    	return marketplacesDetailList;
    }
}
