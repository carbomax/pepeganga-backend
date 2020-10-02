package uy.com.pepeganga.productsservice.gridmodels;

import uy.com.pepeganga.business.common.entities.Category;
import uy.com.pepeganga.business.common.entities.Family;
import uy.com.pepeganga.business.common.models.Image;

import java.io.Serializable;
import java.util.List;

public class ItemGrid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String sku;

	private double priceUYU;

	private double priceUSD;
	
	private long currentStock;

	private List<Category> categories;

	private List<Image> images;

	private Family family;

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

	
}
