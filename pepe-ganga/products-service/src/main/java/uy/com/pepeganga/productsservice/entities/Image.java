package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;


public class Image implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3852172277868398823L;	

	private int id;
	
	public int getId() {
		return id;
	}
	
	private String photo;
	

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
