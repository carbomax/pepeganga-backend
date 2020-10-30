package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderItems {

    private int quantity;

    private double unitPrice;

    private String currencyId;

    private DMOrderItem item;

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("unit_price")
    public double getUnitPrice() {
        return unitPrice;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("item")
    public DMOrderItem getItem() {
        return item;
    }
}
