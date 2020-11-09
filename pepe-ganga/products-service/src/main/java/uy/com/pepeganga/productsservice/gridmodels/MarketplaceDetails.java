package uy.com.pepeganga.productsservice.gridmodels;

public class MarketplaceDetails {

	private Short marketplace;	
	private Integer prodTotal;
	private Integer prodPublished;
	private Integer prodPaused;
	private Integer prodWithoutPublish;
	
	
	public Short getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(Short marketplace) {
		this.marketplace = marketplace;
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
