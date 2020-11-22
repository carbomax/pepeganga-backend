package uy.pepeganga.meli.service.utils;

import uy.com.pepeganga.business.common.utils.enums.MarginType;

import java.util.stream.Stream;

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
