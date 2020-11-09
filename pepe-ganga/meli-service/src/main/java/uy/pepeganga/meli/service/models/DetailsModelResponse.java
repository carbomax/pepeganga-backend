package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import meli.model.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsModelResponse {

    private String idPublication;
    private String siteId;
    private String title;
    private Long sellerId;
    private String categoryId;
    private Integer price;
    private String currencyId;
    private String availableQuantity;
    private String buyingMode;
    private String listingTypeId;
    private String condition;
    private String description;
    private List<AttributeModelResponse> attributes;
    private ShippingModelResponse shipping;
    private List<SaleTermsModelResponse> saleTerms;
    private String lastUpdated;
    private String status;
    private String permalink;
    private List<VariationsModelResponse> variations;
    private String warranty;

    @JsonProperty("id")
    public String getIdPublication() {
        return idPublication;
    }
    @JsonProperty("site_id")
    public String getSiteId() {
        return siteId;
    }
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    @JsonProperty("seller_id")
    public Long getSellerId() {
        return sellerId;
    }
    @JsonProperty("category_id")
    public String getCategoryId() {
        return categoryId;
    }
    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }
    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }
    @JsonProperty("available_quantity")
    public String getAvailableQuantity() {
        return availableQuantity;
    }
    @JsonProperty("buying_mode")
    public String getBuyingMode() {
        return buyingMode;
    }
    @JsonProperty("listing_type_id")
    public String getListingTypeId() {
        return listingTypeId;
    }
    @JsonProperty("condition")
    public String getCondition() {
        return condition;
    }
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
    @JsonProperty("attributes")
    public List<AttributeModelResponse> getAttributes() {
        return attributes;
    }
    @JsonProperty("shipping")
    public ShippingModelResponse getShipping() {
        return shipping;
    }
    @JsonProperty("sale_terms")
    public List<SaleTermsModelResponse> getSaleTerms() {
        return saleTerms;
    }
    @JsonProperty("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
    @JsonProperty("permalink")
    public String getPermalink() {
        return permalink;
    }
    @JsonProperty("variations")
    public List<VariationsModelResponse> getVariations() {
        return variations;
    }
    @JsonProperty("warranty")
    public String getWarranty() {
        return warranty;
    }
}
