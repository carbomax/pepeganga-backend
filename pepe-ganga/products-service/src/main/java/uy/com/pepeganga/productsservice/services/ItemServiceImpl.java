package uy.com.pepeganga.productsservice.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	private final ProductsRepository productsRepository;

	public ItemServiceImpl(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	private Page<Item> findAll(String sku, String nameProduct, Short categoryId, double minPrice,
							   double maxPrice, Pageable pageable) {
		return productsRepository.findAll((Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (minPrice != -1 && maxPrice != -1) {
				predicates.add(cb.between(root.get("precioPesos"), minPrice, maxPrice));
			}
			if (StringUtils.isNotBlank(sku)) {
				predicates.add(cb.like(root.get("sku").as(String.class), "%" + sku + "%"));
			}
			if (StringUtils.isNotBlank(nameProduct)) {
				predicates.add(cb.like(root.get("artDescripCatalogo").as(String.class), "%" + nameProduct + "%"));
			}
			if (categoryId != -1) {
				predicates.add(cb.equal(root.join("categories").get("id").as(Short.class),categoryId));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageable);

	}

	public PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId,
			double minPrice, double maxPrice, Pageable pageable) {
		Page<Item> result = this.findAll(sku.trim(), nameProduct.trim(), categoryId, minPrice,
				maxPrice, pageable);

		List<ItemGrid> itemsGrid = new ArrayList<>();
		result.getContent().forEach(p -> {
			ItemGrid itemGrid = new ItemGrid();
			itemGrid.setCategories(p.getCategories());
			itemGrid.setImages(p.getImage());
			itemGrid.setName(p.getArtDescripCatalogo());
			itemGrid.setPriceUSD(p.getPrecioDolares());
			itemGrid.setPriceUYU(p.getPrecioPesos());
			itemGrid.setSku(p.getSku());
			itemGrid.setCurrentStock(p.getStockActual());
			itemsGrid.add(itemGrid);
		});
		return new PageItemGrid(itemsGrid, result.getTotalPages(), result.getTotalElements(), result.isLast(),
				result.isFirst(), result.getSort(), result.getTotalElements());
	}
}
