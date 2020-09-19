package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.entities.Item;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.services.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	ItemService itemService;

	@RequestMapping("/itemslist")
	public List<ItemGrid> getItemsListGrid(){
		return itemService.getItemsListGrid();
	}
	
	@GetMapping("/items-by-filters/{page}/{size}")
	public ResponseEntity< Page<Item>> getItemsByFilters(@RequestParam String sku, @RequestParam String nameProduct, 
			@RequestParam String categoryNameDescription, @RequestParam double minPrice,@RequestParam double maxPrice, @PathVariable int page, @PathVariable int size){
		return new ResponseEntity<>(itemService.findAll(sku.trim(), nameProduct.trim(), categoryNameDescription.trim(), minPrice, maxPrice, PageRequest.of(page, size)), HttpStatus.OK);
	}
	
}
