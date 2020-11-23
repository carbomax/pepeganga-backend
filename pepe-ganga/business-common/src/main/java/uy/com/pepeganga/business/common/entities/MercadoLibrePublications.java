package uy.com.pepeganga.business.common.entities;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mercadolibrepublications")
public class MercadoLibrePublications implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5478498777102510035L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

	@Column(name="sku")
	private String sku;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="profile_id", nullable = false)
	private Profile profile;
	
	@Column(name="productName")
	private String productName;	
	
	@Column(name="description", columnDefinition = "TEXT")
	private String description;
		
	@Column(name="states")
	private short states;
	
	@Column(name="price_uyu")
	private double priceUYU;

	@Column(name="price_usd")
	private double priceUSD;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "meli_id")
	private List<Image> images;

	@Column(name="currentStock")
	private long currentStock;

	@Column(name="familyId")
	private Short familyId;

	@Column(name="familyDesc")
	private String familyDesc;

	@Column(name="priceCostUYU")
	private double priceCostUYU;

	@Column(name="priceCostUSD")
	private double priceCostUSD;

	@Column(name="deleted")
	private Integer deleted = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	public short getStates() {
		return states;
	}
	public void setStates(short states) {
		this.states = states;
	}
	public double getPriceUYU() {
		return priceUYU;
	}
	public void setPriceUYU(double priceUYU) {
		this.priceUYU = priceUYU;
	}
	public double getPriceUSD() {
		return priceUSD;
	}
	public void setPriceUSD(double priceUSD) {
		this.priceUSD = priceUSD;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

	public Short getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Short familyId) {
		this.familyId = familyId;
	}

	public String getFamilyDesc() {
		return familyDesc;
	}

	public void setFamilyDesc(String familyDesc) {
		this.familyDesc = familyDesc;
	}

	public double getPriceCostUYU() {
		return priceCostUYU;
	}

	public void setPriceCostUYU(double priceCostUYU) {
		this.priceCostUYU = priceCostUYU;
	}

	public double getPriceCostUSD() {
		return priceCostUSD;
	}

	public void setPriceCostUSD(double priceCostUSD) {
		this.priceCostUSD = priceCostUSD;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MercadoLibrePublications)) return false;
		MercadoLibrePublications that = (MercadoLibrePublications) o;
		return Objects.equals(sku, that.sku) &&
				Objects.equals(profile, that.profile);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sku, profile);
	}
}
