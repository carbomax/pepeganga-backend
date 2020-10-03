package uy.com.pepeganga.productsservice.gridmodels;

import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;

public class MarketplaceDetails {

	private MarketplaceType marketplace;	
	private Integer prodTotal;
	private Integer prodPublished;
	private Integer prodPaused;
	private Integer prodWithoutPublish;
	
	
	public MarketplaceType getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(MarketplaceType marketplace) {
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
