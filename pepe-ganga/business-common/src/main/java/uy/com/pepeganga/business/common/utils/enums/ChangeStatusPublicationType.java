package uy.com.pepeganga.business.common.utils.enums;

import java.util.stream.Stream;

public enum  ChangeStatusPublicationType {

    NOT_CHANGED(-1, "error"),
    PAUSED(3, "paused"),
    ACTIVE(4, "active"),
    CLOSED(5, "closed");


    private int code;

    private String status;

    ChangeStatusPublicationType(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public static ChangeStatusPublicationType ofCode(int code) {
        return Stream.of(ChangeStatusPublicationType.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static ChangeStatusPublicationType ofStatus(String status) {
        return Stream.of(ChangeStatusPublicationType.values())
                .filter(p -> p.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
