package uy.com.pepeganga.productsservice.gridmodels;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemGrid {
	
	@JsonProperty()
	private String image;
	private String name;
	private String sku;
	private double priceUYU;
	private double priceUSD;
	private Map<String, String> categories;
	
	public double getPriceUYU() {
		return priceUYU;
	}
	public void setPriceUYU(double priceUYU) {
		this.priceUYU = priceUYU;
	}
	public double getPriceUSD() {
		return priceUSD;
	}
	public void setPriceUSD(double priceUSD) {
		this.priceUSD = priceUSD;
	}
		
	public Map<String, String> getCategories() {
		return categories;
	}	
	public void setCategories(Map<String, String> categories) {
		this.categories = categories;
	}	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
		
}
