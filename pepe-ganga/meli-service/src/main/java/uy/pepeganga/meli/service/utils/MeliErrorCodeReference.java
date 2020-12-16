package uy.pepeganga.meli.service.utils;

public enum MeliErrorCodeReference {
    // Errors type of Mercado Libre
    NOTFOUND_OR_WRONG_CATEGORY("item.category_id.invalid","Categoría no encontrada o No se permite publicar en la categoría seleccionada."),
    PUBLICATION_MODE_WRONG("item.buying_mode.invalid","La categoría no soporta el modos de publicación seleccionado."),
    STATUS_NOT_MODIFIABLE("item.status.not_modifiable", "La publicación puede no existir en mercado libre.");

    private final String code;
    private final String description;

    MeliErrorCodeReference(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
