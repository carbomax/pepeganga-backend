package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.productsservice.clients.IStoreServiceClient;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;

@Service
public class ItemService {

	@Autowired
	IStoreServiceClient client;
	
	public List<ItemGrid> getItemsListGrid()
	{
		return client.getItemsGridTemporally();
	}
}
