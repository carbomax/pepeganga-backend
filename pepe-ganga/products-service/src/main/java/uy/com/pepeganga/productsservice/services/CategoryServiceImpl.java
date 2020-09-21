package uy.com.pepeganga.productsservice.services;

import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Category;
import uy.com.pepeganga.productsservice.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}



	public List<Category> getAllCategories(){

		return (List<Category>)categoryRepository.findAll();
	}
}
