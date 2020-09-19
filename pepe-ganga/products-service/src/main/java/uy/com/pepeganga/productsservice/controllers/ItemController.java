package uy.com.pepeganga.productsservice.controllers;

import java.util.ArrayList;
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
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.services.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	
	final ItemService itemService;	

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}



	@GetMapping("/items-by-filters/{page}/{size}")
	public ResponseEntity<PageItemGrid> getItemsByFilters(@RequestParam String sku, @RequestParam String nameProduct,
			@RequestParam String categoryNameDescription, @RequestParam double minPrice, @RequestParam double maxPrice,
			@PathVariable int page, @PathVariable int size) {
		
		Page<Item> result = itemService.findAll(sku.trim(), nameProduct.trim(), categoryNameDescription.trim(),
				minPrice, maxPrice, PageRequest.of(page, size));
		
		List<ItemGrid> itemsGrid = new ArrayList<>();
		result.getContent().forEach(p -> {
			ItemGrid itemGrid = new ItemGrid();
			itemGrid.setCategories(p.getCategories());
			itemGrid.setImages(p.getImage());
			itemGrid.setName(p.getArtDescripCatalogo());
			itemGrid.setPriceUSD(p.getPrecioDolares());
			itemGrid.setPriceUYU(p.getPrecioPesos());
			itemGrid.setSku(p.getSku());
			itemsGrid.add(itemGrid);
		});
		return new ResponseEntity<>(new PageItemGrid(itemsGrid, result.getTotalPages(), 
				result.getTotalElements(), result.isLast(), result.isFirst(), result.getSort(), result.getTotalElements()), HttpStatus.OK);
	}

}
