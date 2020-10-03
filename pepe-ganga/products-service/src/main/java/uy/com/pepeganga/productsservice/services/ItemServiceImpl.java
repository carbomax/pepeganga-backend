package uy.com.pepeganga.productsservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.utils.conversions.ConversionClass;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;

@Service
public class ItemServiceImpl implements ItemService {

	private final ProductsRepository productsRepository;

	public ItemServiceImpl(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	private Page<Item> findAll(String sku, String nameProduct, Short categoryId, Short familyId, double minPrice,
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

			if(familyId != -1){
				predicates.add(cb.equal(root.get("family").get("id").as(Short.class), familyId));
			}

			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageable);

	}

	@Cacheable("products-storage")
	public PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, Short familyId,
			double minPrice,  double maxPrice, Pageable pageable) {
		Page<Item> result = this.findAll(sku.trim(), nameProduct.trim(), categoryId,  familyId, minPrice,
				maxPrice, pageable);

		List<ItemGrid> itemsGrid = new ArrayList<>();
		result.getContent().forEach(p -> {
			ItemGrid itemGrid = new ItemGrid();
			itemGrid.setCategories(p.getCategories());			
			itemGrid.setImages(ConversionClass.separateImages(p.getImages()));
			itemGrid.setName(p.getArtDescripCatalogo());
			itemGrid.setPriceUSD(p.getPrecioDolares());
			itemGrid.setPriceUYU(p.getPrecioPesos());
			itemGrid.setSku(p.getSku());
			itemGrid.setCurrentStock(p.getStockActual());
			itemGrid.setFamily(p.getFamily());
			itemsGrid.add(itemGrid);
		});
		PageItemGrid pageItemGrid = new PageItemGrid();
		pageItemGrid.setFirst(result.isFirst());
		pageItemGrid.setItemsGrid(itemsGrid);
		pageItemGrid.setLast(result.isLast());
		pageItemGrid.setNumberOfElements(result.getNumberOfElements());
		pageItemGrid.setSort(result.getSort());
		pageItemGrid.setTotalElements(result.getTotalElements());
		pageItemGrid.setTotalPages(result.getTotalPages());
		pageItemGrid.setTotalProducts(productsRepository.count());
		return pageItemGrid;
	}

	public Optional<Item> findItemById(String id) {
		return this.productsRepository.findById(id);
	}
}
