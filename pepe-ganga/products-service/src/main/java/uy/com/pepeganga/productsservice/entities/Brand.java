package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;

public class Brand implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8579951759996328352L;


	private short id;
	
	private String description;
	
	private String marcaInUse;

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

	public String getMarcaInUse() {
		return marcaInUse;
	}

	public void setMarcaInUse(String marcaInUse) {
		this.marcaInUse = marcaInUse;
	}

}
