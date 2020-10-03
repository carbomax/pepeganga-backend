package uy.com.pepeganga.productsservice.models;

import java.util.List;

public class SelectedProducResponse {

	private List<String> existingProducts;
	private Boolean exists;
	
	public Boolean getExists() {
		return exists;
	}
	public void setExists(Boolean exists) {
		this.exists = exists;
	}
	
	public List<String> getExistingProducts() {
		return existingProducts;
	}
	public void setExistingProducts(List<String> existingProducts) {
		this.existingProducts = existingProducts;
	}	
	
}
