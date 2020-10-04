package uy.com.pepeganga.productsservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.MeliPublicationsPK;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.entities.User;
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
	private MeliPublicationsPK pk;
	private MercadoLibrePublications mlp;
	User user;
	
	//Method to fill the details of marketplace card
	public MarketplaceDetails getDetailsMarketplaces(Integer idUser) {	
	
		MarketplaceDetails marketplaces = new MarketplaceDetails();
		
		List<Short> states = mlPublishRepo.findAllStatesByIdUser(idUser);
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
		
		marketplaces.setMarketplace(MarketplaceType.MERCADOLIBRE);
		marketplaces.setProdPaused(pause);
		marketplaces.setProdPublished(publish);
		marketplaces.setProdWithoutPublish(nopublish);
		marketplaces.setProdTotal(total);
		return marketplaces;
	}

	//Method to store the products to publish by user
	public SelectedProducResponse storeProductToPublish(Integer idUser, Short marketplace, List<String> products)
	{			
		user = userRepo.findById(idUser).get();		
		if(marketplace == MarketplaceType.MERCADOLIBRE.getId())
			itemService = new ItemServiceImpl(productsRepository);		
		
		SelectedProducResponse select = new SelectedProducResponse();
		List<String> prodExists = new ArrayList<>();
		List<MercadoLibrePublications> prodToStore = new ArrayList<>();
		
		for (String sku : products) {
			item = new Item();
			item.setSku(sku);
			
			pk = new MeliPublicationsPK();
			pk.setItem(item);
			pk.setUser(user);	
			
			if(mlPublishRepo.existsById(pk)) 
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
					mlp.setUser((User)user);
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
			select.setExists(true);
			select.setExistingProducts(prodExists);
		}
		else {
			select.setExists(false);
		}		
		
		if(!prodToStore.isEmpty())
			for (MercadoLibrePublications mercadoLibrePublications : prodToStore) {
				mlPublishRepo.save(mercadoLibrePublications);	
			}
				
		
		return select;
	}
	
}
