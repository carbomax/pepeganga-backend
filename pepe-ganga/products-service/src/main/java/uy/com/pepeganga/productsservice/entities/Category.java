package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;


public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7824810085308111080L;


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
