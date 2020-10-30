package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.business.common.entities.Image;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.services.MercadoLibrePublishService;

@RestController
@RequestMapping("/api")
public class ProductsSelectedController {

	@Autowired
	MercadoLibrePublishService mlp_services;
	
	@GetMapping("/select-myproducts/{profileEncode}")
	public ResponseEntity<SelectedProducResponse> storeMyProducts(@PathVariable String profileEncode, @RequestParam Short marketplace,
			@RequestParam List<String> products) {			
		return new ResponseEntity<>(mlp_services.storeProductToPublish(profileEncode.trim(), marketplace, products), HttpStatus.CREATED);
	}
	
	@GetMapping("/items-meli-filters/{page}/{size}/{profileEncode}")
	public ResponseEntity<PageItemMeliGrid> getItemsMeliByFilters(@RequestParam String sku, @RequestParam String nameProduct,
			@RequestParam Short state, @RequestParam Short familyId, @RequestParam double minPrice, @RequestParam double maxPrice,
			@PathVariable int page, @PathVariable int size, @PathVariable String profileEncode) {		
		return new ResponseEntity<>(mlp_services.getItemsMeliByFiltersAndPaginator(profileEncode.trim(), sku, nameProduct, state, familyId, minPrice, maxPrice, PageRequest.of(page, size)), HttpStatus.OK);
	}
	
	@PutMapping("/store-common-data/{profileEncode}")
	public ResponseEntity<ReasonResponse> storeCommonData( @PathVariable String profileEncode, @RequestParam String description, @RequestParam List<String> skuList, @RequestBody List<Image> images){
		return new ResponseEntity<>(mlp_services.storeCommonData(profileEncode.trim(), description, skuList, images), HttpStatus.OK);
	}
	
	@PutMapping("/edit-product-info")
	public ResponseEntity<EditableProductModel> updateInfoOfProduct(@RequestBody EditableProductModel product, @RequestParam List<Integer>imagesToDelete){
		try {
			return new ResponseEntity<>(mlp_services.editInfoOfProduct(product, imagesToDelete), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(product, HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping("/product-info/{id}")
	public ResponseEntity<EditableProductModel> getInfoOfProduct(@PathVariable Integer id){
		return new ResponseEntity<>(mlp_services.getCustomProduct(id), HttpStatus.OK);
	}

	@GetMapping("/full-product/{profileEncode}")
	public ResponseEntity<List<MercadoLibrePublications>> getFullProducts(@PathVariable String profileEncode, @RequestParam List<String>skus) {
		try {
			return new ResponseEntity<>(mlp_services.getFullProduct(skus, profileEncode), HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/full-product-id")
	public ResponseEntity<List<EditableProductModel>> getFullProductById(@RequestParam List<Integer> ids) {
		try {
			return new ResponseEntity<>(mlp_services.getFullProductById(ids), HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

}
