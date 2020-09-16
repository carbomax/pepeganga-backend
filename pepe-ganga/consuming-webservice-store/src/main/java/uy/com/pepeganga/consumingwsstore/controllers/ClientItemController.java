package uy.com.pepeganga.consumingwsstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.gridmodels.ItemGrid;
import uy.com.pepeganga.consumingwsstore.models.ItemModelResponse;
import uy.com.pepeganga.consumingwsstore.services.ItemService;


@RestController
@RequestMapping("/api")
public class ClientItemController {

	@Autowired
	ItemService items;
	
	@GetMapping("/items")
	public List<ItemModelResponse> getItems() {
		return items.getItems();
	}
	
	@GetMapping("/itemsgridtemporally")
	public List<ItemGrid> getItemsGridTemporally() {
		return items.getItemsGridTemporally();
	}
	
	@GetMapping("/itemstemporally")
	public List<ItemModelResponse> getItemsTemporally() {
		return items.getItemsTemporally();
	}
}
