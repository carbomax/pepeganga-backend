package uy.com.pepeganga.productsservice.models;

import org.springframework.stereotype.Component;

@Component
public class CategoryModelResponse {

	private short id;
	private String description;
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
