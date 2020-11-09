package uy.com.pepeganga.productsservice.models;

import java.util.List;

import uy.com.pepeganga.business.common.entities.Image;

public class EditableProductModel {

	private Integer id;	
	
	private String productName;		
	
	private String description;		
	
	private short states;	
	
	private double price;	

	private List<Image> images;
	
	/* **No editable fields** */
	private String sku;
	
	private double price_costUYU;

	private double price_costUSD;
	
	private long currentStock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getStates() {
		return states;
	}

	public void setStates(short states) {
		this.states = states;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}
}
