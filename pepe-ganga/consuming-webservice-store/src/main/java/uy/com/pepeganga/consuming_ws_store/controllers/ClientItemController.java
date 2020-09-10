package uy.com.pepeganga.consuming_ws_store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consuming_ws_store.services.ItemService;
import uy.com.pepeganga.consuming_ws_store.wsdl.items.CargaArticulosExecuteResponse;


@RestController
@RequestMapping("/api")
public class ClientItemController {

	@Autowired
	ItemService items;
	
	@GetMapping("/items")
	public CargaArticulosExecuteResponse getItems() {
		return items.getItems();
	}
}
