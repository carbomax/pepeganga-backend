package uy.pepeganga.meli.service.utils;

public enum OrderStatusType {

    PAID("paid"),
    CANCELLED("cancelled");

    private final String status;

    OrderStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
