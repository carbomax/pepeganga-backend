package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.models.CategoryModelResponse;
import uy.com.pepeganga.productsservice.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService category;
	
	@RequestMapping("/allcategories")
	public List<CategoryModelResponse> getAllCategory() {
		return category.getAllCategory();
	}
}
