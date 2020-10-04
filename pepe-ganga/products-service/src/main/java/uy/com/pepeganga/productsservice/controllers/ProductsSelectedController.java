package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.services.MercadoLibrePublishService;

@RestController
@RequestMapping("/api")
public class ProductsSelectedController {

	@Autowired
	MercadoLibrePublishService mlp_services;
	
	@GetMapping("/select-myproducts")
	public ResponseEntity<SelectedProducResponse> storeMyProducts(@RequestParam Integer idUser, @RequestParam Short marketplace,
			@RequestParam List<String> products) {		
		return new ResponseEntity<>(mlp_services.storeProductToPublish(idUser, marketplace, products), HttpStatus.CREATED);
	}	
}
