package uy.com.pepeganga.productsservice.services;

import org.springframework.data.domain.Pageable;

import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;

public interface ItemService {
	
	PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, double minPrice, double maxPrice, Pageable pageable);
}
