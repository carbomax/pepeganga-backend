package uy.com.pepeganga.productsservice.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.business.common.utils.conversions.ConversionClass;
import uy.com.pepeganga.business.common.utils.enums.ActionResult;
import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.business.common.utils.enums.States;
import uy.com.pepeganga.business.common.utils.methods.ConfigurationsSystem;
import uy.com.pepeganga.productsservice.gridmodels.*;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
import uy.com.pepeganga.productsservice.models.RiskTime;
import uy.com.pepeganga.productsservice.models.SelectedProducResponse;
import uy.com.pepeganga.productsservice.repository.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MercadoLibrePublishServiceImpl implements MercadoLibrePublishService {

	private static final Logger logger = LoggerFactory.getLogger(MercadoLibrePublishServiceImpl.class);

	@Autowired
	RiskTime property;
	ConfigurationsSystem configService;

	@Autowired
	MercadoLibrePublishRepository mlPublishRepo;

	@Autowired
	ProductsRepository productsRepository;

	@Autowired
	AccountMeliRepository accountRepository;

	@Autowired
	ItemService itemService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ImageRepository imageRepo;

	@Autowired
	ImagePublicationRepository imagePublicationRepo;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	DetailsPublicationsMeliRepository detailsPublicationsMeliRepository;

	@Autowired
	CheckingStockProcessorRepository checkingStockRepo;

	public MercadoLibrePublishServiceImpl() {
		this.configService = new ConfigurationsSystem();
	}

	private Integer getStockRisk() {
		Integer stock_risk = Integer.parseInt(this.configService.getSynchronizationConfig().get("stock_risk").toString());
		return stock_risk == null ? 0 : stock_risk;
	}

	// Method to fill the details of marketplace card
	@Override
	public MarketplaceDetails getDetailsMarketplaces(Integer idProfile) {

		MarketplaceDetails marketplaces = new MarketplaceDetails();

		List<Short> states = mlPublishRepo.findAllStatesByIdProfile(idProfile);
		int nopublish = 0;
		int publish = 0;
		int total = states.size();

		for (Short s : states) {
			if (s.equals(States.NOPUBLISHED.getId()))
				nopublish++;
			else if (s.equals(States.PUBLISHED.getId()))
				publish++;
		}

		marketplaces.setMarketplace(MarketplaceType.MERCADOLIBRE.getId());
		marketplaces.setProdPublished(publish);
		marketplaces.setProdWithoutPublish(nopublish);
		marketplaces.setProdTotal(total);
		return marketplaces;
	}

	// Method to store the products to publish by user.
	// The products are stored in "Mercado Libre Publication" table.
	@Override
	public SelectedProducResponse storeProductToPublish(String profileEncode, Short marketplace, List<String> products) {

		// Decode data on other side, by processing encoded data
		Integer idProfile = ConversionClass.decodeBase64ToInt(profileEncode);

		Item item;
		MercadoLibrePublications meliPublication;
		MercadoLibrePublications mlp;

		Profile profile = new Profile();
		profile.setId(idProfile);
/*
		if (marketplace == MarketplaceType.MERCADOLIBRE.getId())
			itemService = new ItemServiceImpl(productsRepository);*/

		SelectedProducResponse select = new SelectedProducResponse();
		List<String> prodExists = new ArrayList<>();
		List<MercadoLibrePublications> prodToStore = new ArrayList<>();

		for (String sku : products) {
			item = new Item();
			item.setSku(sku);

			meliPublication = new MercadoLibrePublications();
			meliPublication.setProfile(profile);
			meliPublication.setSku(sku);
			Integer countItem = mlPublishRepo.cantProductSelectedByProfile(idProfile, sku);

			if (countItem > 0) {
				prodExists.add(sku);
			} else {
				Optional<Item> product = itemService.findItemById(sku);
				if (product.isPresent()) {
					mlp = new MercadoLibrePublications();
					mlp.setSku(product.get().getSku());
					mlp.setProfile(profile);
					mlp.setProductName(product.get().getArtDescripCatalogo());
					mlp.setDescription(product.get().getArtDescripML());
					mlp.setPriceUYU(product.get().getPrecioPesos());
					mlp.setPriceUSD(product.get().getPrecioDolares());
					mlp.setCurrentStock(product.get().getStockActual());
					mlp.setPriceCostUSD(product.get().getPrecioDolares());
					mlp.setPriceCostUYU(product.get().getPrecioPesos());
					mlp.setFamilyId(product.get().getFamily().getId());
					mlp.setFamilyDesc(product.get().getFamily().getDescription());
					mlp.setStates(States.NOPUBLISHED.getId());
					mlp.setImages(ConversionClass.separateImages(product.get().getImages()));
					mlp.setSpecialPaused(product.get().getStockActual() <= getStockRisk() ? 1 : 0);
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
				try {
					mlPublishRepo.save(mercadoLibrePublications);
				}catch (Exception e){
					logger.error(String.format("Error storing product with sku: %s in the table: MercadoLibrePublications, Method: storeProductToPublish(), Error: ", mercadoLibrePublications.getSku()), e);
				}
			}

		return select;
	}

	private Page<MercadoLibrePublications> findAll(Integer idProfile, String sku, String nameProduct, Short state, Short familyId, double minPrice,
			double maxPrice, Pageable pageable) {

		return mlPublishRepo.findAll((Root<MercadoLibrePublications> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			Short noPublished = (short)2;
			predicates.add(cb.equal(root.get("states").as(Short.class), noPublished));
			if (idProfile != null) {
				predicates.add(cb.equal(root.join("profile").get("id").as(Integer.class), idProfile));
			}
			if (minPrice != -1 && maxPrice != -1) {
				if(maxPrice == 20000){
					predicates.add(cb.greaterThanOrEqualTo(root.get("priceUYU"), minPrice));
				}
				else{
					predicates.add(cb.between(root.get("priceUYU"), minPrice, maxPrice));
				}
			}
			if (StringUtils.isNotBlank(sku)) {
				predicates.add(cb.like(root.get("sku").as(String.class), "%" + sku + "%"));
			}
			if (StringUtils.isNotBlank(nameProduct)) {
				predicates.add(cb.like(root.get("productName").as(String.class), "%" + nameProduct + "%"));
			}
			/*if (state != -1) {
				predicates.add(cb.equal(root.get("states").as(Short.class), state));
			}*/

			if (familyId != -1) {
				predicates.add(cb.equal(root.get("familyId").as(Short.class), familyId));
			}

			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageable);

	}

	//@CacheEvict(value = "myproducts", allEntries = true)
	@Override
	public PageItemMeliGrid getItemsMeliByFiltersAndPaginator(String profileEncode, String sku, String nameProduct, Short state, Short familyId,
			double minPrice, double maxPrice, Pageable pageable) {

		Integer idProfile = ConversionClass.decodeBase64ToInt(profileEncode);

			Page<MercadoLibrePublications> result = this.findAll(idProfile, sku.trim(), nameProduct.trim(), state, familyId, minPrice, maxPrice,
					pageable);

			List<ItemMeliGrid> itemMeliGridList = new ArrayList<>();
			result.getContent().forEach(p -> {
				ItemMeliGrid itemMeliGrid = new ItemMeliGrid();
				itemMeliGrid.setId(p.getId());
				itemMeliGrid.setState(p.getStates() == States.NOPUBLISHED.getId() ? States.NOPUBLISHED.getValue() : States.PUBLISHED.getValue() );
				itemMeliGrid.setImages(p.getImages());
				itemMeliGrid.setName(p.getProductName());
				itemMeliGrid.setPriceUYU(p.getPriceUYU());
				itemMeliGrid.setPriceUSD(p.getPriceUSD());
				itemMeliGrid.setSku(p.getSku());
				itemMeliGrid.setCurrentStock(p.getCurrentStock());
				Family family = new Family();
				family.setId(p.getFamilyId());
				family.setDescription(p.getFamilyDesc());
				itemMeliGrid.setFamily(family);
				itemMeliGrid.setDescription(p.getDescription());
				itemMeliGrid.setPrice_costUSD(p.getPriceCostUSD());
				itemMeliGrid.setPrice_costUYU(p.getPriceCostUYU());
				itemMeliGrid.setDeleted(p.getDeleted());
				itemMeliGrid.setSpecialPaused(p.getSpecialPaused());
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
	@Override
	public ReasonResponse storeCommonData(String profileEncode, String description,  List<String> skuList, List<Image> images) {
		ReasonResponse reason = new ReasonResponse();
		reason.setSuccess(false);

		if(description.isBlank() && images.isEmpty())
		{
			reason.setReason("Valores de entrada nulos o vacios");
			return reason;
		}

		try {
		List<MercadoLibrePublications> productsToUpdate = new ArrayList<>();
		Integer idProfile = ConversionClass.decodeBase64ToInt(profileEncode);

		for (String sku : skuList) {
			MercadoLibrePublications product = mlPublishRepo.findByItemAndProfile(sku, idProfile);
			if(product != null)
			{
				if(!images.isEmpty()) {
					List<Image> imagesList = product.getImages();
					images.forEach(ima ->{
						String[] parts = ima.getPhotos().split("_");
						int pos = parts[0].lastIndexOf("/");
						String sku_compare = parts[0].substring(pos+1);
						if(sku_compare.equals(sku)) {
							imagesList.add(ima);
						}
					});
					product.setImages(imagesList);
				}
				if(!description.isBlank()) {
					product.setDescription(product.getDescription() + "\n" + description);
				}

				productsToUpdate.add(product);
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

	@Override
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
				store.setSku(prod.get().getSku());
				store.setProfile(prod.get().getProfile());
				store.setDescription(product.getDescription());
				store.setPriceUYU(product.getPrice());
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
				productEditabled.setPrice(result.getPriceUYU());
				productEditabled.setImages(result.getImages());
				productEditabled.setSku(product.getSku());
				productEditabled.setPrice_costUYU(product.getPrice_costUYU());
				productEditabled.setPrice_costUSD(product.getPrice_costUSD());
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

	public DMDetailsPublicationsMeli editInfoOfPublication(DMDetailsPublicationsMeli product, List<Integer>imagesToDelete ) throws Exception
	{
		DetailsPublicationsMeli result;
		try {
			Optional<DetailsPublicationsMeli> optionalDetail = detailsPublicationsMeliRepository.findById(product.getId());
			if(optionalDetail.isPresent()) {
				if(imagesToDelete == null || imagesToDelete.isEmpty()) {}
				else {
					for (Integer ima : imagesToDelete) {
						if(imagePublicationRepo.existsById(ima))
							imagePublicationRepo.deleteById(ima);
					}
				}
				DetailsPublicationsMeli store = optionalDetail.get();
				store.setDescription(product.getDescription());
				store.setTitle(product.getTitle());
				store.setImages(product.getImages());
				if(product.getPriceEditProduct() != store.getPriceEditProduct()){
					store.setPriceEditProduct(product.getPriceEditProduct());
					store.setPricePublication((int) product.getPriceEditProduct());
					store.setMargin(null);
				}
				result = detailsPublicationsMeliRepository.save(store);

				//Producto a devolver
				DMDetailsPublicationsMeli detailPublication = new DMDetailsPublicationsMeli();
				detailPublication.setId(result.getId());
				detailPublication.setPriceEditProduct(result.getPriceEditProduct());
				detailPublication.setPriceCostUYU(result.getPriceCostUYU());
				detailPublication.setPricePublication(result.getPricePublication());
				detailPublication.setDescription(result.getDescription());
				detailPublication.setImages(result.getImages());
				detailPublication.setSku(result.getSku());
				detailPublication.setAccountMeli(result.getAccountMeli());
				detailPublication.setAccountName(product.getAccountName());
				detailPublication.setCurrentStock(product.getCurrentStock());
				detailPublication.setCategoryMeli(result.getCategoryMeli());
				detailPublication.setIdPublicationMeli(result.getIdPublicationMeli());
				detailPublication.setLastUpgrade(result.getLastUpgrade());
				detailPublication.setMargin(result.getMargin());
				detailPublication.setMlPublicationId(result.getIdMLPublication());
				detailPublication.setPermalink(result.getPermalink());
				detailPublication.setStatus(result.getStatus());
				detailPublication.setSaleStatus(result.getSaleStatus());
				detailPublication.setTitle(result.getTitle());
				detailPublication.setWarrantyTime(result.getWarrantyTime());
				detailPublication.setWarrantyType(result.getWarrantyType());
				return detailPublication;
			}
			//NO se encuntra el producto en base dato, devuelve exception
			throw new Exception();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
	}

	@Override
	public EditableProductModel getCustomProduct(Integer id)
	{
		EditableProductModel editP= new EditableProductModel();
		if(id == null)
			return editP;

		Optional<MercadoLibrePublications> item = mlPublishRepo.findById(id);
		if(item.isPresent()) {
			editP.setId(item.get().getId());
			editP.setCurrentStock(item.get().getCurrentStock());
			editP.setDescription(item.get().getDescription());
			editP.setPrice(item.get().getPriceUYU());
			editP.setPrice_costUYU(item.get().getPriceCostUYU());
			editP.setPrice_costUSD(item.get().getPriceCostUSD());
			editP.setProductName(item.get().getProductName());
			editP.setSku(item.get().getSku());
			editP.setStates(item.get().getStates());
			editP.setImages(item.get().getImages());
		}
		return editP;
	}

	public DMDetailsPublicationsMeli getDetailPublication(Integer id)
	{
		DMDetailsPublicationsMeli detailPublication = new DMDetailsPublicationsMeli();
		if(id == null) {
			return detailPublication;
		}
		Optional<DetailsPublicationsMeli> result = detailsPublicationsMeliRepository.findById(id);
		if(result.isPresent()) {

			Optional<SellerAccount> seller = Optional.ofNullable(accountRepository.findByUserId(result.get().getUserId()));

			//Producto a devolver
			detailPublication.setId(result.get().getId());
			detailPublication.setPriceEditProduct(result.get().getPriceEditProduct());
			detailPublication.setPriceCostUYU(result.get().getPriceCostUYU());
			detailPublication.setPricePublication(result.get().getPricePublication());
			detailPublication.setDescription(result.get().getDescription());
			detailPublication.setImages(result.get().getImages());
			detailPublication.setSku(result.get().getSku());
			detailPublication.setAccountMeli(result.get().getAccountMeli());
			detailPublication.setAccountName(seller.isPresent() ? seller.get().getBusinessName() : "");
			detailPublication.setCurrentStock(itemService.findItemById(result.get().getSku()).get().getStockActual());
			detailPublication.setCategoryMeli(result.get().getCategoryMeli());
			detailPublication.setIdPublicationMeli(result.get().getIdPublicationMeli());
			detailPublication.setLastUpgrade(result.get().getLastUpgrade());
			detailPublication.setMargin(result.get().getMargin());
			detailPublication.setMlPublicationId(result.get().getIdMLPublication());
			detailPublication.setPermalink(result.get().getPermalink());
			detailPublication.setStatus(result.get().getStatus());
			detailPublication.setSaleStatus(result.get().getSaleStatus());
			detailPublication.setTitle(result.get().getTitle());
			detailPublication.setWarrantyTime(result.get().getWarrantyTime());
			detailPublication.setWarrantyType(result.get().getWarrantyType());
		}
		return detailPublication;
	}

	@Override
	public List<MercadoLibrePublications> getFullProduct(List<String> skus, String profileEncode) throws Exception {

		Integer idProfile = ConversionClass.decodeBase64ToInt(profileEncode);
		List<MercadoLibrePublications> productList = new ArrayList<>();
		for (String sku: skus) {
			MercadoLibrePublications product = mlPublishRepo.findByItemAndProfile(sku, idProfile);
			if(product != null) {
				productList.add(product);
			}
		}
		if(!productList.isEmpty()) {
			return productList ;
		}
		else{
			throw new Exception("Producto no encontrado");
		}
	}

	@Override
	public List<EditableProductModel> getFullProductById(List<Integer> ids) throws Exception {
		List<EditableProductModel> result = new ArrayList<>();
		for (Integer id: ids) {
			var product = getCustomProduct(id);
			if(product != null){
				result.add(product);
			}
		}
		return result;

	}

	//Method to DetailPublication (product published) Table
	@Override
	public PageDeatilsPublicationMeli getPublicationsDetailsBySellerProfile(Integer profileId, String sku, String idMeliPublication, int meliAccount, String typeStateSearch, String title, int page, int size) {
		String mySKU = sku.isBlank() ? "" : sku.trim();
		String myMeliPubl = idMeliPublication.isBlank() ? "" : idMeliPublication.trim();
		String myTypeStatus = typeStateSearch.isBlank() ? "" : typeStateSearch.trim();
		String myTitle = title.isBlank() ? "" : title.trim();

		Optional<Profile> profile = profileRepository.findById(profileId);
		if (profile.isPresent()) {
			List<Integer> accountIdList = new ArrayList<>();
			if(meliAccount == -1){
				accountIdList = profile.get().getSellerAccounts().stream().map(SellerAccount::getId).collect(Collectors.toList());
			}
			else{
				accountIdList.add(meliAccount);
			}
			Page<DetailsPublicationsMeli> detailsPublication = detailsPublicationsMeliRepository.findDetailsPublicationByFilter(mySKU, myMeliPubl, accountIdList, myTypeStatus, myTitle, PageRequest.of(page, size));

			List<DMDetailsPublicationsMeli> publicationsMeliGrids =  new ArrayList<>();
			detailsPublication.getContent().forEach(details -> {
			if(itemService.findItemById(details.getSku()).isPresent()) {
				DMDetailsPublicationsMeli publicationsMeliGrid = new DMDetailsPublicationsMeli();
				publicationsMeliGrid.setAccountName(
						Objects.requireNonNull(profile.get().getSellerAccounts()
								.stream().filter(account -> account.getId().equals(details.getAccountMeli())).findFirst().orElse(null)).getBusinessName()
				);
				publicationsMeliGrid.setAccountMeli(details.getAccountMeli());
				publicationsMeliGrid.setMlPublicationId(details.getIdMLPublication());
				publicationsMeliGrid.setCategoryMeli(details.getCategoryMeli());
				publicationsMeliGrid.setId(details.getId());
				publicationsMeliGrid.setIdPublicationMeli(details.getIdPublicationMeli());
				publicationsMeliGrid.setImages(details.getImages());
				publicationsMeliGrid.setMargin(details.getMargin());
				publicationsMeliGrid.setLastUpgrade(details.getLastUpgrade());
				publicationsMeliGrid.setPermalink(details.getPermalink());
				publicationsMeliGrid.setPricePublication((int) details.getPricePublication());
				publicationsMeliGrid.setPriceCostUYU(details.getPriceCostUYU());
				publicationsMeliGrid.setPriceEditProduct(details.getPriceEditProduct());
				publicationsMeliGrid.setTitle(details.getTitle());
				publicationsMeliGrid.setSku(details.getSku());
				publicationsMeliGrid.setStatus(details.getStatus());
				publicationsMeliGrid.setDescription(details.getDescription());
				publicationsMeliGrid.setCurrentStock(itemService.findItemById(details.getSku()).get().getStockActual());
				publicationsMeliGrid.setSaleStatus(details.getSaleStatus());
				publicationsMeliGrid.setDeleted(details.getDeleted());
				publicationsMeliGrid.setSpecialPaused(details.getSpecialPaused());
				publicationsMeliGrids.add(publicationsMeliGrid);
				}
				else{
					CheckingStockProcessor checking = checkingStockRepo.findBySku(details.getSku());
					if(checking == null) {
						checking = new CheckingStockProcessor();
					}
					checking.setRealStock(0);
					checking.setExpectedStock(0);
					checking.setAction(1);
					checkingStockRepo.save(checking);
				}

			});

			return new PageDeatilsPublicationMeli(publicationsMeliGrids, detailsPublication.getNumberOfElements(), detailsPublication.getTotalElements());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profileId));
		}
	}

	@Override
	public Boolean deleteProductsOfStore(List<Integer> products){
		products.forEach(this::deleteProductOfStore);
		return true;
	}

	@Override
	public Boolean deleteProductOfStore(Integer product){
		//detailsPublicationsMeliRepository.updateMLPublicationsField(product);
		mlPublishRepo.deleteById(product);
		return true;
	}

	@Override
	public boolean existProductInMeliStorage(Integer profileId, String sku) {
		return mlPublishRepo.countByProfileIdAndSku(profileId, sku) > 0;
	}
}
