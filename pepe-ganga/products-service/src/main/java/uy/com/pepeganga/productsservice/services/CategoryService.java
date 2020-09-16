package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.productsservice.clients.IStoreServiceClient;
import uy.com.pepeganga.productsservice.models.CategoryModelResponse;

@Service
public class CategoryService {

	@Autowired
	IStoreServiceClient client;
	
	public List<CategoryModelResponse> getAllCategory(){
		
		return client.getCategories();
	}
}
