package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;
import java.util.List;



public class Family implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8039538839590766050L;


	private Short id;
	
	private String description;	

	private List<SubFamily> subfamilies;
	
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

	public List<SubFamily> getSubfamilies() {
		return subfamilies;
	}

	public void setSubfamilies(List<SubFamily> subfamilies) {
		this.subfamilies = subfamilies;
	}

}
