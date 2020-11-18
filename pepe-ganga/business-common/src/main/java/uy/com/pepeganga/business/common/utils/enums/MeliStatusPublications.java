package uy.com.pepeganga.business.common.utils.enums;

public enum MeliStatusPublications {
    ACTIVE("active", (short) 1),
    UNDER_REVIEW("under_review", (short) 2),
    PAUSED("paused", (short) 3),
    CLOSED("closed", (short) 4),
    INACTIVE("inactive", (short) 5),
    PAYMENT_REQUIRED("payment required", (short) 6),
    FAIL("fail", (short) 7),
    IN_PROCESS("in process", (short) 8);

    private final String value;
    private final short id;

    MeliStatusPublications(String value, short id) {
        this.value = value;
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public short getId() {
        return id;
    }
}
