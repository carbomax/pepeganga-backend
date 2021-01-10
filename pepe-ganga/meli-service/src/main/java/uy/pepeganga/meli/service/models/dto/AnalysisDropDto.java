package uy.pepeganga.meli.service.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AnalysisDropDto implements Serializable {

    private String date;

    private int totalSalesCount;

    private double totalAmount;

    private transient List<SalesAndAmountBySellerDto> salesAndAmountBySeller;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalSalesCount() {
        return totalSalesCount;
    }

    public void setTotalSalesCount(int totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("sellerAnalysisDrop")
    public List<SalesAndAmountBySellerDto> getSalesAndAmountBySeller() {
        return salesAndAmountBySeller;
    }

    public void setSalesAndAmountBySeller(List<SalesAndAmountBySellerDto> salesAndAmountBySeller) {
        this.salesAndAmountBySeller = salesAndAmountBySeller;
    }
}
