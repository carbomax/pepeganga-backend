package uy.pepeganga.meli.service.utils;

import java.util.stream.Stream;

public enum Flex {

    UNDEFINED(-1, "Sin Confirmar"),
    YES(1, "SI"),
    NO(0, "NO");

    private final Integer code;
    private final String  reportValue;

    Flex(Integer code, String  reportValue) {
        this.code = code;
        this.reportValue = reportValue;
    }

    public static Flex of(Integer code){
        return Stream.of(Flex.values())
                .filter(flex -> flex.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public String getReportValue() {
        return reportValue;
    }
}
