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
	
	private double price_cost;
	
	private long currentStock;

	private AccountMarginModel account_margin;

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

	public double getPrice_cost() {
		return price_cost;
	}

	public void setPrice_cost(double price_cost) {
		this.price_cost = price_cost;
	}

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

	public AccountMarginModel getAccount_margin() {
		return account_margin;
	}

	public void setAccount_margin(AccountMarginModel account_margin) {
		this.account_margin = account_margin;
	}
}
