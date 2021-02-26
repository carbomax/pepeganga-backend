package uy.com.pepeganga.productsservice.models;

import java.io.Serializable;

public class SearchItem implements Serializable {

    private String sku;
    private String nameProduct;
    private Short categoryId;
    private Short familyId;
    private double minPrice;
    private double maxPrice;
    private Integer profileId;
    // -1 - Nothing, 0 - not exist, 1 - exist
    private int existToPublish;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }

    public Short getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Short familyId) {
        this.familyId = familyId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public int getExistToPublish() {
        return existToPublish;
    }

    public void setExistToPublish(int existToPublish) {
        this.existToPublish = existToPublish;
    }
}
