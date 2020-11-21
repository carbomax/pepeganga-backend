package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMCostComponents {

    private Double especialDiscount;
    private Double loyalDiscount;
    private Double compensation;
    private Double gapDiscount;
    private Double ratio;


    @JsonProperty("special_discount")
    public Double getEspecialDiscount() {
        return especialDiscount;
    }

    public void setEspecialDiscount(Double especialDiscount) {
        this.especialDiscount = especialDiscount;
    }

    @JsonProperty("loyal_discount")
    public Double getLoyalDiscount() {
        return loyalDiscount;
    }

    public void setLoyalDiscount(Double loyalDiscount) {
        this.loyalDiscount = loyalDiscount;
    }

    @JsonProperty("compensation")
    public Double getCompensation() {
        return compensation;
    }

    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    @JsonProperty("gap_discount")
    public Double getGapDiscount() {
        return gapDiscount;
    }

    public void setGapDiscount(Double gapDiscount) {
        this.gapDiscount = gapDiscount;
    }

    @JsonProperty("ratio")
    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

}
