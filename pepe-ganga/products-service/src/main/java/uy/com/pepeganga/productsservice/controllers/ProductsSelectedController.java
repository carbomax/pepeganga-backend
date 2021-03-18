package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uy.com.pepeganga.business.common.entities.Image;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.CommonInfoRequest;
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

	/**** OJO *** Posible a sustituir por segundo metodo *****/
	@PutMapping("/store-common-data/{profileEncode}")
	public ResponseEntity<ReasonResponse> storeCommonData( @PathVariable String profileEncode, @RequestParam String description, @RequestParam List<String> skuList, @RequestBody List<Image> images){
		return new ResponseEntity<>(mlp_services.storeCommonData(profileEncode.trim(), description, skuList, images), HttpStatus.OK);
	}

	@PutMapping("/store-common-data2/{profileEncode}")
	public ResponseEntity<ReasonResponse> storeCommonData2( @PathVariable String profileEncode, @RequestParam String description, @RequestBody List<CommonInfoRequest> commonInfoList){
		return new ResponseEntity<>(mlp_services.storeCommonData2(profileEncode.trim(), description, commonInfoList), HttpStatus.OK);
	}

	/** Se termina aqui **/
	
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

	@DeleteMapping("/delete-products/{productsList}")
	public ResponseEntity<Boolean> deleteProductsOfStore(@PathVariable List<Integer> productsList){
		try {
			if(productsList.isEmpty())
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
			return new ResponseEntity<Boolean>(mlp_services.deleteProductsOfStore(productsList), HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(false, HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/delete-product/{product}")
	public ResponseEntity<Boolean> deleteProductsOfStore(@PathVariable Integer product){
		try {
			if(product == null)
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
			return new ResponseEntity<Boolean>(mlp_services.deleteProductOfStore(product), HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(false, HttpStatus.CONFLICT);
		}
	}

}
