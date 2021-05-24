package uy.com.pepeganga.business.common.utils.enums;

import java.util.stream.Stream;

public enum MeliStatusPublications {
    ACTIVE("active", (short) 1, "Activa"),
    UNDER_REVIEW("under_review", (short) 2, "Bajo revisión"),
    PAUSED("paused", (short) 3, "Pausada"),
    CLOSED("closed", (short) 4, "Finalizada"),
    INACTIVE("inactive", (short) 5, "Inactiva"),
    PAYMENT_REQUIRED("payment required", (short) 6, "Pago requerido"),
    FAIL("fail", (short) 7, "Fallida"),
    IN_PROCESS("in process", (short) 8, "En proceso");

    private final String value;
    private final short id;
    private final String reportValue;

    MeliStatusPublications(String value, short id, String reportValue) {
        this.value = value;
        this.id = id;
        this.reportValue = reportValue;
    }

    public static MeliStatusPublications of(String value) {
        return Stream.of(MeliStatusPublications.values())
                .filter(p -> p.value.equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getValue() {
        return value;
    }
    public short getId() {
        return id;
    }

    public String getReportValue() {
        return reportValue;
    }
}
