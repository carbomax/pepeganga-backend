package uy.com.pepeganga.productsservice.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.productsservice.clients.IStoreServiceClient;

@Service
public class FamilyService {

	@Autowired
	IStoreServiceClient client;
	
	public Map<String, String> getAllFamiliesSubFamilies(){
		
		return client.getAllFamiliesSubFamilies();
	}
}
