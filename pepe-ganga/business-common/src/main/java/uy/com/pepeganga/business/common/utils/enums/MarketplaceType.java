package uy.com.pepeganga.business.common.utils.enums;

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
}

