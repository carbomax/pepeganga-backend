package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertiesWithSalesRequest {
    private Integer price;
    private PicturesRequest pictures;

    public PicturesRequest getPictures() {
        return pictures;
    }

    public void setPictures(PicturesRequest pictures) {
        this.pictures = pictures;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
