package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.IScheduledSyncService;
import uy.com.pepeganga.consumingwsstore.services.ItemRequestService;


@RestController
@RequestMapping("/api")
public class ClientItemController {

	@Autowired
	ItemRequestService items;
	@Autowired
	IScheduledSyncService service;
	
	@GetMapping("/store/items")
	public void storeItems() {
		//items.storeItems();
	}
	
	@GetMapping("/store/deleteitems")
	public void deleteItem() {
		items.deleteItem();
	}

	@GetMapping("/automatized")
	public void automatized() {
		service.syncDataBase();
	}
	
}
