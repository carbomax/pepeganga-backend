package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.business.common.utils.enums.UtilsClass.States;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.repository.MercadoLibrePublishRepository;

@Service
public class MercadoLibrePublishServiceImpl implements MercadoLibrePublishService  {
	/*
	@Autowired
	MercadoLibrePublishRepository mlPublishRepo;
	
	public MarketplaceDetails getDetailsMarketplaces(Integer idUser) {	
	
		MarketplaceDetails marketplaces = new MarketplaceDetails();
		
		List<String> states = mlPublishRepo.findAllStatesByIdUser(idUser);
		int nopublish = 0;
		int publish = 0;
		int pause = 0;
		int total = states.size();
		
		for (String s : states) {
			if(s.equals(States.NoPublicado))			
				nopublish++;		
			else if(s.equals(States.Pausado))			
				pause++;			
			else if(s.equals(States.Publicado))			
				publish++;	
		}
		
		
		marketplaces.setIdMP((short)1);
		marketplaces.setNameMP(MarketplaceType.MERCADOLIBRE.getValue());
		marketplaces.setProdPaused(pause);
		marketplaces.setProdPublished(publish);
		marketplaces.setProdWithoutPublish(nopublish);
		marketplaces.setProdTotal(total);
		return marketplaces;
	}*/
}
