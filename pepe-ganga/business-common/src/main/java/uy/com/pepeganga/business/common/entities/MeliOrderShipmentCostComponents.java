package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meli_order_shipment_cost_components")
public class MeliOrderShipmentCostComponents implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double especialDiscount = 0.0;
    private Double loyalDiscount = 0.0;
    private Double compensation = 0.0;
    private Double gapDiscount = 0.0;
    private Double ratio = 0.0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEspecialDiscount() {
        return especialDiscount;
    }

    public void setEspecialDiscount(Double especialDiscount) {
        this.especialDiscount = especialDiscount;
    }

    public Double getLoyalDiscount() {
        return loyalDiscount;
    }

    public void setLoyalDiscount(Double loyalDiscount) {
        this.loyalDiscount = loyalDiscount;
    }

    public Double getCompensation() {
        return compensation;
    }

    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    public Double getGapDiscount() {
        return gapDiscount;
    }

    public void setGapDiscount(Double gapDiscount) {
        this.gapDiscount = gapDiscount;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
