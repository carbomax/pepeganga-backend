package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionRequest {
    private String description;

    @JsonProperty("plain_text")
    public String getDescription() {
        return description;
    }

    @JsonProperty("plain_text")
    public void setDescription(String description) {
        this.description = description;
    }

}
