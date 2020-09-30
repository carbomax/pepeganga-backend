package uy.com.pepeganga.productsservice.gridmodels;

public class MarketplaceDetails {

	private String nameMP;
	private Short idMP;
	private Integer prodTotal;
	private Integer prodPublished;
	private Integer prodPaused;
	private Integer prodWithoutPublish;
	
	public String getNameMP() {
		return nameMP;
	}
	public void setNameMP(String nameMP) {
		this.nameMP = nameMP;
	}
	public Short getIdMP() {
		return idMP;
	}
	public void setIdMP(Short idMP) {
		this.idMP = idMP;
	}
	public Integer getProdTotal() {
		return prodTotal;
	}
	public void setProdTotal(Integer prodTotal) {
		this.prodTotal = prodTotal;
	}
	public Integer getProdPublished() {
		return prodPublished;
	}
	public void setProdPublished(Integer prodPublished) {
		this.prodPublished = prodPublished;
	}
	public Integer getProdPaused() {
		return prodPaused;
	}
	public void setProdPaused(Integer prodPaused) {
		this.prodPaused = prodPaused;
	}
	public Integer getProdWithoutPublish() {
		return prodWithoutPublish;
	}
	public void setProdWithoutPublish(Integer prodWithoutPublish) {
		this.prodWithoutPublish = prodWithoutPublish;
	}
}
