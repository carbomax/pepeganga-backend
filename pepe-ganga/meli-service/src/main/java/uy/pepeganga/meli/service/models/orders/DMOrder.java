package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrder {

    private Long id;

    private String status;

    private String dateCreated;

    private String dateClosed;

    private List<DMOrderItems> orderItems;

    private double totalAmount;

    private double paidAmount;

    private String currencyId;

    private List<DMOrderPayment> payments;

    private DMOrderBuyer buyer;

    private DMOrderSeller seller;

    private DMOrderShipping shipping;

    private DMOrderTaxes taxes;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("date_created")
    public String getDateCreated() {
        return dateCreated;
    }

    @JsonProperty("date_closed")
    public String getDateClosed() {
        return dateClosed;
    }

    @JsonProperty("order_items")
    public List<DMOrderItems> getOrderItems() {
        return orderItems;
    }

    @JsonProperty("total_amount")
    public double getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("payments")
    public List<DMOrderPayment> getPayments() {
        return payments;
    }

    @JsonProperty("buyer")
    public DMOrderBuyer getBuyer() {
        return buyer;
    }

    @JsonProperty("seller")
    public DMOrderSeller getSeller() {
        return seller;
    }

    @JsonProperty("shipping")
    public DMOrderShipping getShipping() {
        return shipping;
    }

    @JsonProperty("taxes")
    public DMOrderTaxes getTaxes() {
        return taxes;
    }

    @JsonProperty("paid_amount")
    public double getPaidAmount() {
        return paidAmount;
    }
}