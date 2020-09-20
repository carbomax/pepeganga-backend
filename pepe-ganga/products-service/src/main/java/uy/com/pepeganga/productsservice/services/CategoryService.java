package uy.com.pepeganga.productsservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import uy.com.pepeganga.productsservice.entities.Category;
import uy.com.pepeganga.productsservice.repository.CategoryRepository;

@Service
public class CategoryService {

	final CategoryRepository categoryRepository;
	
	
	
	public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}



	public List<Category> getAllCategories(){		
		return (List<Category>) categoryRepository.findAll();
	}
}
