package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.entities.Category;
import uy.com.pepeganga.productsservice.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory() {
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
}
