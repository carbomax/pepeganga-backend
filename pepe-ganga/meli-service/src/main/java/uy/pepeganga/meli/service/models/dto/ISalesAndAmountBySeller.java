package uy.pepeganga.meli.service.models.dto;

public interface ISalesAndAmountBySeller {

    double getAmount();

    int getSalesCount();

    long getSellerId();

    String getSellerName();
}
