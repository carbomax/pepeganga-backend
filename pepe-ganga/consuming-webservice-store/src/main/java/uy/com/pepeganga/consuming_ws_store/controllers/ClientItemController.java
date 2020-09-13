package uy.com.pepeganga.consuming_ws_store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consuming_ws_store.models.ItemModelResponse;
import uy.com.pepeganga.consuming_ws_store.services.ItemService;


@RestController
@RequestMapping("/api")
public class ClientItemController {

	@Autowired
	ItemService items;
	
	@GetMapping("/items")
	public List<ItemModelResponse> getItems() {
		return items.getItems();
	}
	
	@GetMapping("/itemsTemporally")
	public List<ItemModelResponse> getItemsTemporally() {
		return items.getItemsTemporally();
	}
}
