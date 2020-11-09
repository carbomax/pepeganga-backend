package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pictures {
    private List<Source> pictures;

    @JsonProperty("pictures")
    public List<Source> getPictures() {
        return pictures;
    }
    @JsonProperty("pictures")
    public void setPictures(List<Source> pictures) {
        this.pictures = pictures;
    }
}
