package uy.com.pepeganga.productsservice.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.com.pepeganga.productsservice.gridmodels.ItemGrid;
import uy.com.pepeganga.productsservice.models.CategoryModelResponse;
import uy.com.pepeganga.productsservice.models.ItemModelResponse;


@FeignClient(name="consuming-store-service" )
public interface IStoreServiceClient {

	@GetMapping("/api/itemstemporally")
	public List<ItemModelResponse> getItemsTemporally();
	
	@GetMapping("/api/itemsgridtemporally")
	public List<ItemGrid> getItemsGridTemporally();
	
	@GetMapping("/api/categories")
	public List<CategoryModelResponse> getCategories();
	
	@GetMapping("/api/familiesandsubfamilies")
	public Map<String, String> getAllFamiliesSubFamilies();
	
	
}
