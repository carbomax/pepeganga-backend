package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.CategoryRequestService;


@RestController
@RequestMapping("/api")
public class ClientCategoryController {

	@Autowired
	CategoryRequestService category;
	
	@GetMapping("/store/categories")
	public void storeCategories() {				
		 category.storeCategories();
	}
}
