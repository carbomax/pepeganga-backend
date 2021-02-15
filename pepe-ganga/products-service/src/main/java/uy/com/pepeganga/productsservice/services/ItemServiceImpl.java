package uy.com.pepeganga.productsservice.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.utils.conversions.ConversionClass;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.repository.MercadoLibrePublishRepository;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;
import uy.com.pepeganga.productsservice.repository.ProfileRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ProductsRepository productsRepository;


	@Autowired
	MercadoLibrePublishService publishService;

	@Autowired
	ProfileRepository profileRepository;

	private Page<Item> findAll(String sku, String nameProduct, Short categoryId, Short familyId, double minPrice,
							   double maxPrice,  Pageable pageable) {
		return productsRepository.findAll((Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (minPrice != -1 && maxPrice != -1) {
				if(maxPrice == 20000){
					predicates.add(cb.greaterThanOrEqualTo(root.get("precioPesos"), minPrice));
				}
				else{
					predicates.add(cb.between(root.get("precioPesos"), minPrice, maxPrice));
				}
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

//	@Cacheable("storage")
	public PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, Short familyId,
			double minPrice,  double maxPrice, Integer profileId , Pageable pageable) {
		Page<Item> result = this.findAll(sku.trim(), nameProduct.trim(), categoryId,  familyId, minPrice,
				maxPrice, pageable);

		Optional<Profile> profile = profileRepository.findById(profileId);
		if(profile.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profileId));
		}

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
			itemGrid.setExistInMeliStore(publishService.existProductInMeliStorage(profileId, p.getSku()));
			itemsGrid.add(itemGrid);
		});
		PageItemGrid pageItemGrid = new PageItemGrid();
		pageItemGrid.setItemsGrid(itemsGrid);
		pageItemGrid.setTotalElements(result.getTotalElements());
		pageItemGrid.setFirst(result.isFirst());
		pageItemGrid.setLast(result.isLast());
		pageItemGrid.setNumberOfElements(result.getNumberOfElements());
		pageItemGrid.setSort(result.getSort());

		pageItemGrid.setTotalPages(result.getTotalPages());
		pageItemGrid.setTotalProducts(productsRepository.count());
		return pageItemGrid;
	}



	public Optional<Item> findItemById(String id) {
		return this.productsRepository.findById(id);
	}
}
