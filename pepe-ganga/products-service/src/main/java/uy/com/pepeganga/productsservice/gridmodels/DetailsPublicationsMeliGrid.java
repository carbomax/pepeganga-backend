package uy.com.pepeganga.productsservice.gridmodels;

import java.io.Serializable;
import java.util.List;

public class DetailsPublicationsMeliGrid implements Serializable {

    private Integer id;

    private Integer mlPublicationId;

    private Integer accountMeli;

    private String idPublicationMeli;

    private String title;

    private String permalink;

    private String categoryMeli;

    private Integer pricePublication;

    private String warrantyType;

    private String warrantyTime;

    private Short margin;

    private String lastUpgrade;

    private String status;

    private String sku;

    private String accountName;

    private List<String> images;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
