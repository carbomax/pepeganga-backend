package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;


public class SubFamily implements Serializable {

	private static final long serialVersionUID = 3578114223188114511L;

	private SubFamilyPK subFamilyPK;

	public SubFamily(Short id, String description) {
		super();
		this.subFamilyPK = new SubFamilyPK(id, description);
	}

	public Short getId() {
		return this.subFamilyPK.getId();
	}

	public void setId(Short id) {
		this.subFamilyPK.setId(id);
	}

	public String getDescription() {
		return this.subFamilyPK.getDescription();
	}

	public void setDescription(String description) {
		this.subFamilyPK.setDescription(description);
	}

}
