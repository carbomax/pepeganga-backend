package uy.com.pepeganga.productsservice.gridmodels;

import uy.com.pepeganga.business.common.entities.Image;
import uy.com.pepeganga.business.common.entities.ImageDetailPublications;

import java.io.Serializable;
import java.util.List;

public class DMDetailsPublicationsMeli implements Serializable {

    private Integer id;

    private Integer mlPublicationId;

    private Integer accountMeli;

    private String idPublicationMeli;

    private String title;

    private String permalink;

    private String categoryMeli;

    private Integer pricePublication;

    private Double priceCostUYU;

    private double priceEditProduct;

    private String warrantyType;

    private String warrantyTime;

    private Short margin;

    private String lastUpgrade;

    private String status;

    private String sku;

    private String accountName;

    private List<ImageDetailPublications> images;

    private String description;

    private long currentStock;

    private Integer saleStatus;

    private int totalElements;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMlPublicationId() {
        return mlPublicationId;
    }

    public void setMlPublicationId(Integer mlPublicationId) {
        this.mlPublicationId = mlPublicationId;
    }

    public Integer getAccountMeli() {
        return accountMeli;
    }

    public void setAccountMeli(Integer accountMeli) {
        this.accountMeli = accountMeli;
    }

    public String getIdPublicationMeli() {
        return idPublicationMeli;
    }

    public void setIdPublicationMeli(String idPublicationMeli) {
        this.idPublicationMeli = idPublicationMeli;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getCategoryMeli() {
        return categoryMeli;
    }

    public void setCategoryMeli(String categoryMeli) {
        this.categoryMeli = categoryMeli;
    }

    public Integer getPricePublication() {
        return pricePublication;
    }

    public void setPricePublication(Integer pricePublication) {
        this.pricePublication = pricePublication;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(String warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public Short getMargin() {
        return margin;
    }

    public void setMargin(Short margin) {
        this.margin = margin;
    }

    public String getLastUpgrade() {
        return lastUpgrade;
    }

    public void setLastUpgrade(String lastUpgrade) {
        this.lastUpgrade = lastUpgrade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<ImageDetailPublications> getImages() {
        return images;
    }

    public void setImages(List<ImageDetailPublications> images) {
        this.images = images;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(long currentStock) {
        this.currentStock = currentStock;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public Double getPriceCostUYU() {
        return priceCostUYU;
    }

    public void setPriceCostUYU(Double priceCostUYU) {
        this.priceCostUYU = priceCostUYU;
    }

    public double getPriceEditProduct() {
        return priceEditProduct;
    }

    public void setPriceEditProduct(double priceEditProduct) {
        this.priceEditProduct = priceEditProduct;
    }
}
