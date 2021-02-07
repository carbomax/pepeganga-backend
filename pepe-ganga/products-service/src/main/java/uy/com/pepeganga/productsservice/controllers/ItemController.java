package uy.com.pepeganga.productsservice.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.services.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/items-by-filters/{page}/{size}")
	public ResponseEntity<PageItemGrid> getItemsByFilters(@RequestParam String sku, @RequestParam String nameProduct,
			@RequestParam Short categoryId, @RequestParam Short familyId, @RequestParam double minPrice, @RequestParam double maxPrice,
			@PathVariable int page, @PathVariable int size, @RequestParam(defaultValue = "0") Integer profileId) {
		return new ResponseEntity<>(itemService.getItemsByFiltersAndPaginator(sku, nameProduct, categoryId, familyId, minPrice, maxPrice, profileId, PageRequest.of(page, size)), HttpStatus.OK);
	}

}
