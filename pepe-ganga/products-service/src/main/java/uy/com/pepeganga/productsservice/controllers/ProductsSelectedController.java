package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.services.MercadoLibrePublishService;

@RestController
@RequestMapping("/api")
public class ProductsSelectedController {

	@Autowired
	MercadoLibrePublishService mlp_services;
	
	@GetMapping("/select-myproducts")
	public ResponseEntity<SelectedProducResponse> storeMyProducts(@RequestParam Integer idProfile, @RequestParam Short marketplace,
			@RequestParam List<String> products) {		
		return new ResponseEntity<>(mlp_services.storeProductToPublish(idProfile, marketplace, products), HttpStatus.CREATED);
	}
	
	@GetMapping("/items-meli-filters/{page}/{size}")
	public ResponseEntity<PageItemMeliGrid> getItemsMeliByFilters(@RequestParam String sku, @RequestParam String nameProduct,
			@RequestParam Short state, @RequestParam Short familyId, @RequestParam double minPrice, @RequestParam double maxPrice,
			@PathVariable int page, @PathVariable int size) {		
		return new ResponseEntity<>(mlp_services.getItemsMeliByFiltersAndPaginator(sku, nameProduct, state, familyId, minPrice, maxPrice, PageRequest.of(page, size)), HttpStatus.OK);
	}
}
