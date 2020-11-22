package uy.com.pepeganga.business.common.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stockprocessor")
public class StockProcessor implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private Integer expectedStock;

    private Integer realStock;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
