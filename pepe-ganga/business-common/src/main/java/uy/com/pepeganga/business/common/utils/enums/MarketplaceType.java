package uy.com.pepeganga.business.common.utils.enums;

import java.util.stream.Stream;

public enum MarketplaceType {

    MERCADOLIBRE("Mercado Libre UY", (short) 1),
    AMAZON("Amazon", (short) 2);
    

    private final String value;
    private final short id;

    MarketplaceType(String value, short id) {
        this.value = value;
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public short getId() {
        return id;
    }

    public static MarketplaceType of(int code) {
        return Stream.of(MarketplaceType.values())
                .filter(p -> p.getId() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

