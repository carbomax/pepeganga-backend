package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingModelResponse {

    private String mode;

    private Boolean localPickUp;

    private Boolean freeShipping;

    private List<String> tags;

    private String logisticType;

    private Boolean storePickUp;

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }
    @JsonProperty("local_pick_up")
    public Boolean getLocalPickUp() {
        return localPickUp;
    }
    @JsonProperty("free_shipping")
    public Boolean getFreeShipping() {
        return freeShipping;
    }
    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }
    @JsonProperty("logistic_type")
    public String getLogisticType() {
        return logisticType;
    }
    @JsonProperty("store_pick_up")
    public Boolean getStorePickUp() {
        return storePickUp;
    }
}
