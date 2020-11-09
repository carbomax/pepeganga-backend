package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderItem {

    private String id;

    private String title;

    private String categoryId;

    private String sellerSku;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @JsonProperty("category_id")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("seller_sku")
    public String getSellerSku() {
        return sellerSku;
    }
}
