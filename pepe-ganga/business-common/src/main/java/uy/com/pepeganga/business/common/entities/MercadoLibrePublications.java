package uy.com.pepeganga.business.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(MeliPublicationsPK.class)
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
		
	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="productName")
	private String productName;	
	
	@Column(name="description", columnDefinition = "TEXT")
	private String description;
		
	@Column(name="states")
	private String states;
	
	@Column(name="price")
	private double price;
	
	@Column(name="sendsEnabled")
	private boolean sendsEnabled;
	
	@Column(name="images", columnDefinition = "blob", length = 10000)
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MercadoLibrePublications other = (MercadoLibrePublications) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
		
	
}
