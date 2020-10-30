package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import meli.model.VariationsAttributeCombinations;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VariationsModelResponse {
    private Integer price;
    private Integer availableQuantity;
    private Integer soldQuantity;

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }
    @JsonProperty("available_quantity")
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
    @JsonProperty("sold_quantity")
    public Integer getSoldQuantity() {
        return soldQuantity;
    }

}
