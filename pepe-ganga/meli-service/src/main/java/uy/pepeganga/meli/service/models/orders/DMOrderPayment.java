package uy.pepeganga.meli.service.models.orders;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderPayment {

    private Long id;

    private double transactionAmount;

    private String  currencyId;

    private String status;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("transaction_amount")
    public double getTransactionAmount() {
        return transactionAmount;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
}
