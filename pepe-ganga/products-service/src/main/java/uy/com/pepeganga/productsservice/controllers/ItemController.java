package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.services.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	ItemService item;

	@RequestMapping("/itemslist")
	public List<ItemGrid> getItemsListGrid(){
		return item.getItemsListGrid();
	}
	
	
	
}
