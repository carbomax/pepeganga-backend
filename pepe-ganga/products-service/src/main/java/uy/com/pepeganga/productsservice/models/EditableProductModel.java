package uy.com.pepeganga.productsservice.models;

import java.util.List;
import uy.com.pepeganga.business.common.models.Image;

public class EditableProductModel {

	private Integer id;	
	
	private String productName;		
	
	private String description;		
	
	private short states;	
	
	private double price;	

	private List<Image> images;

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
	
	
}
