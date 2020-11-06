package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertiesWithoutSalesRequest {
    private String title;
    private Integer price;
    private PicturesRequest pictures;

    public PicturesRequest getPictures() {
        return pictures;
    }

    public void setPictures(PicturesRequest pictures) {
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
