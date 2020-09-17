package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;


public class SubFamilyPK implements Serializable {
	
	/**
	 * 
	 */	
	private static final long serialVersionUID = 9182501762196831861L;
	
	private Short id;
	private String description;
		
	public SubFamilyPK(Short id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
