package uy.com.pepeganga.productsservice.services;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;

public interface ItemService {
	
	PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, Short familyId, double minPrice, double maxPrice, Pageable pageable);

	public Optional<Item> findItemById(String id);
}
