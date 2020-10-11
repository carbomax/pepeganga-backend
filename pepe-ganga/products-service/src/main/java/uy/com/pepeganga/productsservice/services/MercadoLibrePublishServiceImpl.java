package uy.com.pepeganga.productsservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uy.com.pepeganga.business.common.entities.Image;
import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.business.common.utils.conversions.ConversionClass;
import uy.com.pepeganga.business.common.utils.enums.ActionResult;
import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.business.common.utils.enums.States;
import uy.com.pepeganga.productsservice.gridmodels.ItemMeliGrid;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.gridmodels.PageItemMeliGrid;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.repository.ImageRepository;
import uy.com.pepeganga.productsservice.repository.MercadoLibrePublishRepository;
import uy.com.pepeganga.productsservice.repository.ProductsRepository;
import uy.com.pepeganga.productsservice.repository.UserRepository;

@Service
public class MercadoLibrePublishServiceImpl implements MercadoLibrePublishService {

	@Autowired
	MercadoLibrePublishRepository mlPublishRepo;

	@Autowired
	ProductsRepository productsRepository;

	@Autowired
	ItemService itemService;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ImageRepository imageRepo;

	// Method to fill the details of marketplace card
	public MarketplaceDetails getDetailsMarketplaces(Integer idProfile) {

		MarketplaceDetails marketplaces = new MarketplaceDetails();

		List<Short> states = mlPublishRepo.findAllStatesByIdProfile(idProfile);
		int nopublish = 0;
		int publish = 0;
		int pause = 0;
		int total = states.size();

		for (Short s : states) {
			if (s.equals(States.NOPUBLISHED.getId()))
				nopublish++;
			else if (s.equals(States.PAUSED.getId()))
				pause++;
			else if (s.equals(States.PUBLISHED.getId()))
				publish++;
		}

		marketplaces.setMarketplace(MarketplaceType.MERCADOLIBRE.getId());
		marketplaces.setProdPaused(pause);
		marketplaces.setProdPublished(publish);
		marketplaces.setProdWithoutPublish(nopublish);
		marketplaces.setProdTotal(total);
		return marketplaces;
	}

	// Method to store the products to publish by user
	public SelectedProducResponse storeProductToPublish(Integer idProfile, Short marketplace, List<String> products) {
		Item item;
		MercadoLibrePublications meliPublication;
		MercadoLibrePublications mlp;
		
		Profile profile = new Profile();
		profile.setId(idProfile);
		
		if (marketplace == MarketplaceType.MERCADOLIBRE.getId())
			itemService = new ItemServiceImpl(productsRepository);

		SelectedProducResponse select = new SelectedProducResponse();
		List<String> prodExists = new ArrayList<>();
		List<MercadoLibrePublications> prodToStore = new ArrayList<>();

		for (String sku : products) {
			item = new Item();
			item.setSku(sku);

			meliPublication = new MercadoLibrePublications();
			meliPublication.setProfile(profile);
			meliPublication.setItem(item);
			Integer countItem = mlPublishRepo.cantProductSelectedByProfile(idProfile, sku);

			if (countItem > 0) {
				prodExists.add(sku);
			} else {
				Optional<Item> product = itemService.findItemById(sku);
				if (product.isPresent()) {
					mlp = new MercadoLibrePublications();
					mlp.setItem((Item) product.get());
					mlp.setProfile(profile);
					mlp.setProductName(product.get().getArtDescripCatalogo());
					mlp.setDescription(product.get().getArtDescripML());
					mlp.setPrice(product.get().getPrecioPesos());
					mlp.setStates(States.NOPUBLISHED.getId());
					mlp.setImages(ConversionClass.separateImages(product.get().getImages()));
					prodToStore.add(mlp);
				}
			}
		}

		if (!prodExists.isEmpty()) {
			select.setExistingProducts(prodExists);
			if (products.size() == prodExists.size())
				select.setCodeResult(ActionResult.BAD);
			else
				select.setCodeResult(ActionResult.PARTIAL);
		} else {
			select.setCodeResult(ActionResult.DONE);
		}

		if (!prodToStore.isEmpty())
			for (MercadoLibrePublications mercadoLibrePublications : prodToStore) {
				mlPublishRepo.save(mercadoLibrePublications);
			}

		return select;
	}

	private Page<MercadoLibrePublications> findAll(String sku, String nameProduct, Short state, Short familyId, double minPrice,
			double maxPrice, Pageable pageable) {
		return mlPublishRepo.findAll((Root<MercadoLibrePublications> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (minPrice != -1 && maxPrice != -1) {
				predicates.add(cb.between(root.join("item").get("precioPesos"), minPrice, maxPrice));
			}
			if (StringUtils.isNotBlank(sku)) {
				predicates.add(cb.like(root.join("item").get("sku").as(String.class), "%" + sku + "%"));
			}
			if (StringUtils.isNotBlank(nameProduct)) {
				predicates.add(cb.like(root.get("productName").as(String.class), "%" + nameProduct + "%"));
			}
			if (state != -1) {
				predicates.add(cb.equal(root.get("states").as(Short.class), state));
			}

			if (familyId != -1) {
				predicates.add(cb.equal(root.join("item").get("family").get("id").as(Short.class), familyId));
			}

			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageable);

	}

	//@CacheEvict(value = "myproducts", allEntries = true)
	public PageItemMeliGrid getItemsMeliByFiltersAndPaginator(String sku, String nameProduct, Short state, Short familyId,
			double minPrice, double maxPrice, Pageable pageable) {
		Page<MercadoLibrePublications> result = this.findAll(sku.trim(), nameProduct.trim(), state, familyId, minPrice, maxPrice,
				pageable);

		List<ItemMeliGrid> itemMeliGridList = new ArrayList<>();
		result.getContent().forEach(p -> {
			ItemMeliGrid itemMeliGrid = new ItemMeliGrid();
			itemMeliGrid.setId(p.getId());
			itemMeliGrid.setState(p.getStates() == States.NOPUBLISHED.getId() ? States.NOPUBLISHED.getValue() : p.getStates() == States.PUBLISHED.getId() ? States.PUBLISHED.getValue() : States.PAUSED.getValue());
			itemMeliGrid.setImages(p.getImages());
			itemMeliGrid.setName(p.getProductName());			
			itemMeliGrid.setPriceUYU(p.getPrice());
			itemMeliGrid.setSku(p.getItem().getSku());
			itemMeliGrid.setCurrentStock(p.getItem().getStockActual());
			itemMeliGrid.setFamily(p.getItem().getFamily());
			itemMeliGridList.add(itemMeliGrid);
		});
		PageItemMeliGrid pageItemMeliGrid = new PageItemMeliGrid();
		pageItemMeliGrid.setFirst(result.isFirst());
		pageItemMeliGrid.setItemsMeliGridList(itemMeliGridList);
		pageItemMeliGrid.setLast(result.isLast());
		pageItemMeliGrid.setNumberOfElements(result.getNumberOfElements());
		pageItemMeliGrid.setSort(result.getSort());
		pageItemMeliGrid.setTotalElements(result.getTotalElements());
		pageItemMeliGrid.setTotalPages(result.getTotalPages());
		pageItemMeliGrid.setTotalProducts(mlPublishRepo.count());
		return pageItemMeliGrid;
	}
	
	public ReasonResponse storeCommonData(Integer idProfile, String description,  List<String> skuList, List<Image> images) {
		ReasonResponse reason = new ReasonResponse();
		reason.setSuccess(false);
		
		if(description.isBlank() && images.isEmpty())
		{
			reason.setReason("Valores de entrada nulos o vacios");
			return reason;
		}			
		
		try {
		List<MercadoLibrePublications> productsToUpdate = new ArrayList<>();
				
		for (String sku : skuList) {
			Optional<MercadoLibrePublications> product = Optional.of(mlPublishRepo.findByItemAndProfile(sku, idProfile));
			if(product.isPresent())
			{
				if(!images.isEmpty()) {
					List<Image> imagesList = product.get().getImages();
					imagesList.addAll(images);						
					product.get().setImages(imagesList);
				}
				if(!description.isBlank()) {
					product.get().setDescription(new String(product.get().getDescription()+ "\n" + description));
				}
				
				productsToUpdate.add(product.get());
			}			
		}		
		
			mlPublishRepo.saveAll(productsToUpdate);
			reason.setSuccess(true);			
		}
		catch (Exception e) {
			// TODO: handle exception
			reason.setSuccess(false);
			reason.setReason("error al almacenar valores");			
		}
		return reason;
	}
	
	public EditableProductModel editInfoOfProduct(EditableProductModel product, List<Integer>imagesToDelete ) throws Exception
	{
		MercadoLibrePublications result;
		ReasonResponse reason = new ReasonResponse();
		reason.setSuccess(false);
		try {				
			Optional<MercadoLibrePublications> prod = mlPublishRepo.findById(product.getId());
			if(prod.isPresent()) {
				
				if(imagesToDelete == null || imagesToDelete.isEmpty()) {}
				else {
					for (Integer ima : imagesToDelete) {
						if(imageRepo.existsById(ima))
							imageRepo.deleteById(ima);
					}			
				}	
				
				MercadoLibrePublications store = prod.get();
				store.setItem(prod.get().getItem());
				store.setProfile(prod.get().getProfile());
				store.setDescription(product.getDescription());
				store.setPrice(product.getPrice());
				store.setProductName(product.getProductName());
				store.setStates(product.getStates());
				
				if(!product.getImages().isEmpty()) {			
					store.setImages(product.getImages());
				}
				result = mlPublishRepo.save(store);	
				reason.setSuccess(true);
				
				//Producto a devolver
				EditableProductModel productEditabled = new EditableProductModel();
				productEditabled.setId(result.getId());
				productEditabled.setProductName(result.getProductName());
				productEditabled.setDescription(result.getDescription());
				productEditabled.setStates(result.getStates());
				productEditabled.setPrice(result.getPrice());
				productEditabled.setImages(result.getImages());
				productEditabled.setSku(product.getSku());
				productEditabled.setPrice_cost(product.getPrice_cost());
				productEditabled.setCurrentStock(product.getCurrentStock());
				return productEditabled;
			}
				//NO se encuntra el producto en base dato, devuelve exception				
			throw new Exception();	
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
	}

	public EditableProductModel getCustomProduct(Integer id)
	{
		EditableProductModel editP= new EditableProductModel();
		if(id == null) 
			return editP;
		
		Optional<MercadoLibrePublications> item = mlPublishRepo.findById(id);	
		if(item.isPresent()) {
			editP.setId(item.get().getId());
			editP.setCurrentStock(item.get().getItem().getStockActual());
			editP.setDescription(item.get().getDescription());
			editP.setPrice(item.get().getPrice());
			editP.setPrice_cost(item.get().getItem().getPrecioPesos());
			editP.setProductName(item.get().getProductName());
			editP.setSku(item.get().getItem().getSku());
			editP.setStates(item.get().getStates());
			editP.setImages(item.get().getImages());			
		} 
		return editP;
	}
}
