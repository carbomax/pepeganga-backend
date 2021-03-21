package uy.com.pepeganga.productsservice.services;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.models.SearchItem;

public interface ItemService {
	
	PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, Short familyId, double minPrice, double maxPrice, Integer profileId,Pageable pageable);

	Optional<Item> findItemById(String id);

	PageItemGrid getItemsByFiltersAndPaginator(SearchItem searchItem, PageRequest of);
}
