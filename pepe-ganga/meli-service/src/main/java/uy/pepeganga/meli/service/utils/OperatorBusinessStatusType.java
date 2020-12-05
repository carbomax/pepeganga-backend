package uy.pepeganga.meli.service.utils;

import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;

import java.util.stream.Stream;

public enum OperatorBusinessStatusType {

    // 0 - In process, 1 - Undelivered, 2 - Delivered
    IN_PROCESS(0,"in_process"),
    UNDELIVERED(1,"undelivered"),
    DELIVERED(2, "delivered");

    private final int code;
    private final String status;

    OperatorBusinessStatusType(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public static OperatorBusinessStatusType ofByStatus(String status) {
        return Stream.of(OperatorBusinessStatusType.values())
                .filter(s -> s.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
