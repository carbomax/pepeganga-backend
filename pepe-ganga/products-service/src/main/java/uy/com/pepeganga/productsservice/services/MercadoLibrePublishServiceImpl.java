package uy.com.pepeganga.productsservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.utils.enums.ActionResult;
import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.business.common.utils.enums.States;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.repository.MercadoLibrePublishRepository;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;
import uy.com.pepeganga.productsservice.repository.UserRepository;

@Service
public class MercadoLibrePublishServiceImpl implements MercadoLibrePublishService  {
	
	@Autowired
	MercadoLibrePublishRepository mlPublishRepo;
	
	@Autowired
	ProductsRepository productsRepository;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	UserRepository userRepo;
	
	private Item item;
	private MercadoLibrePublications meliPublication;
	private MercadoLibrePublications mlp;
	Profile profile;
	
	//Method to fill the details of marketplace card
	public MarketplaceDetails getDetailsMarketplaces(Integer idProfile) {	
	
		MarketplaceDetails marketplaces = new MarketplaceDetails();
		
		List<Short> states = mlPublishRepo.findAllStatesByIdProfile(idProfile);
		int nopublish = 0;
		int publish = 0;
		int pause = 0;
		int total = states.size();
		
		for (Short s : states) {
			if(s.equals(States.NOPUBLISHED.getId()))			
				nopublish++;		
			else if(s.equals(States.PAUSED.getId()))			
				pause++;			
			else if(s.equals(States.PUBLISHED.getId()))			
				publish++;	
		}		
		
		marketplaces.setMarketplace(MarketplaceType.MERCADOLIBRE.getId());
		marketplaces.setProdPaused(pause);
		marketplaces.setProdPublished(publish);
		marketplaces.setProdWithoutPublish(nopublish);
		marketplaces.setProdTotal(total);
		return marketplaces;
	}

	//Method to store the products to publish by user
	public SelectedProducResponse storeProductToPublish(Integer idProfile, Short marketplace, List<String> products)
	{				
		profile = new Profile();
		profile.setId(idProfile);
		if(marketplace == MarketplaceType.MERCADOLIBRE.getId())
			itemService = new ItemServiceImpl(productsRepository);		
		
		SelectedProducResponse select = new SelectedProducResponse();
		List<String> prodExists = new ArrayList<>();
		List<MercadoLibrePublications> prodToStore = new ArrayList<>();
		
		for (String sku : products) {
			item = new Item();
			item.setSku(sku);
			
			meliPublication = new MercadoLibrePublications();
			meliPublication.setProfile(profile);
			meliPublication.setItem(item);
			Integer countItem = mlPublishRepo.cantProductSelectedByProfile(idProfile, sku);
			
			if(countItem > 0) 
			{
				prodExists.add(sku);		
			}
			else
			{
				Optional<Item> product = itemService.findItemById(sku);
				if(product.isPresent())
				{
					mlp = new MercadoLibrePublications();
					mlp.setItem((Item)product.get());
					mlp.setProfile(profile);
					mlp.setProductName(product.get().getArtDescripCatalogo());
					mlp.setDescription(product.get().getArtDescripML());
					mlp.setPrice(product.get().getPrecioPesos());
					mlp.setStates(States.NOPUBLISHED.getId());
					mlp.setImages(product.get().getImages());					
					prodToStore.add(mlp);
				}
			}
		}		
		
		if(!prodExists.isEmpty()) {
			select.setExistingProducts(prodExists);
			if(products.size() == prodExists.size())			
				select.setCodeResult(ActionResult.BAD);
			else
				select.setCodeResult(ActionResult.PARTIAL);	
		}
		else {
			select.setCodeResult(ActionResult.DONE);
		}		
		
		if(!prodToStore.isEmpty())
			for (MercadoLibrePublications mercadoLibrePublications : prodToStore) {
				mlPublishRepo.save(mercadoLibrePublications);	
			}
				
		
		return select;
	}
	
}
