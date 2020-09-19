package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.ItemRequestService;


@RestController
@RequestMapping("/api")
public class ClientItemController {

	@Autowired
	ItemRequestService items;
	
	@GetMapping("/store/items")
	public void storeItems() {
		items.storeItems();
	}
	
	@GetMapping("/storeItemsTemporally")
	public void storeItemsTemporally() {
		items.storeItemsTemporally();
	}
	
}
