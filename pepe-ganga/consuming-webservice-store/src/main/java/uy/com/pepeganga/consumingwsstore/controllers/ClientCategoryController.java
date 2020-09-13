package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.CategoryService;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecuteResponse;


@RestController
@RequestMapping("/api")
public class ClientCategoryController {

	@Autowired
	CategoryService category;
	
	@GetMapping("/categories")
	public CargaZafrasExecuteResponse getCategories() {
		return category.getCategories();
	}
}
