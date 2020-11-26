package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "checkingstockprocessor")
public class CheckingStockProcessor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sku;

    private Integer expectedStock;

    private Integer realStock;

    // 0 - to paused, 1 - to delete
    private Integer action;

    public CheckingStockProcessor(String sku, Integer expectedStock, Integer realStock) {
        this.sku = sku;
        this.expectedStock = expectedStock;
        this.realStock = realStock;
    }

    public CheckingStockProcessor() {
        // do nothing
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getExpectedStock() {
        return expectedStock;
    }

    public void setExpectedStock(Integer expectedStock) {
        this.expectedStock = expectedStock;
    }

    public Integer getRealStock() {
        return realStock;
    }

    public void setRealStock(Integer realStock) {
        this.realStock = realStock;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckingStockProcessor that = (CheckingStockProcessor) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(sku, that.sku)) return false;
        if (!Objects.equals(expectedStock, that.expectedStock))
            return false;
        if (!Objects.equals(realStock, that.realStock)) return false;
        return Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (expectedStock != null ? expectedStock.hashCode() : 0);
        result = 31 * result + (realStock != null ? realStock.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
