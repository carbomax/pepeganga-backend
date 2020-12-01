package uy.com.pepeganga.consumingwsstore.models;

import java.io.Serializable;

public class Pair implements Serializable {
    private String sku;
    private Integer stock;

    public Pair() {}
    public Pair(String sku, Integer stock) {
        this.sku = sku;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
