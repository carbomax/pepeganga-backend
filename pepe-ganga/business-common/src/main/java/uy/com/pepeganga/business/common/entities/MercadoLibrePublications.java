package uy.com.pepeganga.business.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mercadolibrepublications")
public class MercadoLibrePublications implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5478498777102510035L;	

	@Id	
	@ManyToOne	
	@JoinColumn(name="item_id")
	private Item item;
	/*
	@Id	
	@ManyToOne
	@Column(name="user_id")
	private User user;
	*/
	@Column(name="productName")
	private String productName;	
	
	@Column(name="description")
	private String description;
		
	@Column(name="states")
	private String states;
	
	@Column(name="price")
	private double price;
	
	@Column(name="sendsEnabled")
	private boolean sendsEnabled;
	
	@Column(name="images")
	private String images;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isSendsEnabled() {
		return sendsEnabled;
	}
	public void setSendsEnabled(boolean sendsEnabled) {
		this.sendsEnabled = sendsEnabled;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
		
	
}
