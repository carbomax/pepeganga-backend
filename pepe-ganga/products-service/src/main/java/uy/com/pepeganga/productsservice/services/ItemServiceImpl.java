package uy.com.pepeganga.productsservice.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.utils.conversions.ConversionClass;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.gridmodels.PageItemGrid;
import uy.com.pepeganga.productsservice.models.SearchItem;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;
import uy.com.pepeganga.productsservice.repository.ProfileRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

	private Page<Item> findAll(SearchItem searchItem,  List<String> skus,  Pageable pageable) {
		return productsRepository.findAll((Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (searchItem.getMinPrice() != -1 && searchItem.getMaxPrice() != -1) {
				if(searchItem.getMaxPrice() == 20000){
					predicates.add(cb.greaterThanOrEqualTo(root.get("precioPesos"), searchItem.getMinPrice()));
				}
				else{
					predicates.add(cb.between(root.get("precioPesos"), searchItem.getMinPrice(), searchItem.getMaxPrice()));
				}
			}
			if (StringUtils.isNotBlank(searchItem.getSku())) {
				predicates.add(cb.like(root.get("sku").as(String.class), "%" + searchItem.getSku().trim() + "%"));
			}
			if (StringUtils.isNotBlank(searchItem.getNameProduct())) {
				predicates.add(cb.like(root.get("artDescripCatalogo").as(String.class), "%" + searchItem.getNameProduct().trim() + "%"));
			}
			if (searchItem.getCategoryId() != -1) {
				predicates.add(cb.equal(root.join("categories").get("id").as(Short.class), searchItem.getCategoryId()));
			}

			if(searchItem.getFamilyId() != -1){
				predicates.add(cb.equal(root.get("family").get("id").as(Short.class), searchItem.getFamilyId()));
			}

			if(!skus.isEmpty()){
				predicates.add(root.get("sku").as(String.class).in(skus));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageable);

	}


	//	@Cacheable("storage")
	public PageItemGrid getItemsByFiltersAndPaginator(String sku, String nameProduct, Short categoryId, Short familyId,
			double minPrice,  double maxPrice, Integer profileId , Pageable pageable) {

		Optional<Profile> profile = profileRepository.findById(profileId);
		if(profile.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profileId));
		}

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

	@Override
	public PageItemGrid getItemsByFiltersAndPaginator(SearchItem searchItem, PageRequest pageable)  {
		Optional<Profile> profile = profileRepository.findById(searchItem.getProfileId());
		if(profile.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", searchItem.getProfileId()));
		}


		List<String> skusNotInToPublish;

		if(searchItem.getExistToPublish() == 1 ){
			skusNotInToPublish = productsRepository.getSkusInToPublish(searchItem.getProfileId());
		} else if(searchItem.getExistToPublish() == 0) {
			skusNotInToPublish = productsRepository.getSkusNotInToPublish(searchItem.getProfileId());
		}else {
			skusNotInToPublish = Collections.emptyList();
		}

		Page<Item> result = this.findAll(searchItem, skusNotInToPublish, pageable);

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
			if(searchItem.getExistToPublish() == 1 ){
				itemGrid.setExistInMeliStore(true);
			} else if(searchItem.getExistToPublish() == 0) {
				itemGrid.setExistInMeliStore(false);
			}else {
				itemGrid.setExistInMeliStore(publishService.existProductInMeliStorage(searchItem.getProfileId(), p.getSku()));
			}

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
}
