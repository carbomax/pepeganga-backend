package uy.com.pepeganga.productsservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.Category;
import uy.com.pepeganga.productsservice.services.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

	private final CategoryServiceImpl categoryService;

	public CategoryController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory() {
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
}
