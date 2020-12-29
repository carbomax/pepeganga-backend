package uy.pepeganga.meli.service.models.dto;

import java.io.Serializable;

public class StockVsTotalItemDto implements Serializable {

    private Integer withStock;

    private Integer total;

    public Integer getWithStock() {
        return withStock;
    }

    public void setWithStock(Integer withStock) {
        this.withStock = withStock;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
