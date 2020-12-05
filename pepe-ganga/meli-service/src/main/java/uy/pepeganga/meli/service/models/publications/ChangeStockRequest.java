package uy.pepeganga.meli.service.models.publications;

import java.io.Serializable;

public class ChangeStockRequest implements Serializable {
    private Integer available_quantity;

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }
}
