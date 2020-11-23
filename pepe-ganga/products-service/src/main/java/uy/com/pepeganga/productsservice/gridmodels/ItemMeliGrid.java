package uy.com.pepeganga.productsservice.gridmodels;

import java.io.Serializable;
import java.util.List;

import uy.com.pepeganga.business.common.entities.Family;
import uy.com.pepeganga.business.common.entities.Image;

public class ItemMeliGrid implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347842736023677325L;

	private Integer id;
	
	private String name;
	
	private String sku;

	private double priceUYU;

	private double priceUSD;
	
	private Family family;
	
	private long currentStock;
	
	private String state;

	private List<Image> images;

	private double price_costUYU;

	private double price_costUSD;

	private String description;

	private Integer deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public double getPriceUSD() {
		return priceUSD;
	}

	public void setPriceUSD(double priceUSD) {
		this.priceUSD = priceUSD;
	}

	public double getPrice_costUYU() {
		return price_costUYU;
	}

	public void setPrice_costUYU(double price_costUYU) {
		this.price_costUYU = price_costUYU;
	}

	public double getPrice_costUSD() {
		return price_costUSD;
	}

	public void setPrice_costUSD(double price_costUSD) {
		this.price_costUSD = price_costUSD;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
