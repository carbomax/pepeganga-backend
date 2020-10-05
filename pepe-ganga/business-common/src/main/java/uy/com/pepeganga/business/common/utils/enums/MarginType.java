package uy.com.pepeganga.business.common.utils.enums;

import java.util.stream.Stream;

public enum MarginType {
    SIMPLE(1),
    PERCENT(2);

    private int code;

    MarginType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    public static MarginType of(int code) {
        return Stream.of(MarginType.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
