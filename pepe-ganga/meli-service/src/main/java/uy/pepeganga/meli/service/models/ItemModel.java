package uy.pepeganga.meli.service.models;

import meli.model.Item;

public class ItemModel {
    private Item item;
    private Integer idProduct;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
}
