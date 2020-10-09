package uy.com.pepeganga.productsservice.gridmodels;

import java.io.Serializable;
import java.util.List;

import uy.com.pepeganga.business.common.entities.Family;
import uy.com.pepeganga.business.common.models.Image;

public class ItemMeliGrid implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347842736023677325L;

	private Integer id;
	
	private String name;
	
	private String sku;

	private double priceUYU;
	
	private Family family;
	
	private long currentStock;
	
	private String state;
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private List<Image> images;

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

	public double getPriceUYU() {
		return priceUYU;
	}

	public void setPriceUYU(double priceUYU) {
		this.priceUYU = priceUYU;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	
	
}
