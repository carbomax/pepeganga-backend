package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderShipping {

    private Long id;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }
}
