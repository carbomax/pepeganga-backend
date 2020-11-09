package uy.com.pepeganga.business.common.utils.enums;

import java.util.stream.Stream;

public enum MeliStatusAccount {
    UNDEFINED(0),
    AUTHORIZED(1),
    SYNCHRONIZED(2);

    private int code;

    MeliStatusAccount(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    public static MeliStatusAccount of(int code) {
        return Stream.of(MeliStatusAccount.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
