package uy.com.pepeganga.userservice.models.system_config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationConfig {
    private String flex;
    private String publication_type;

    @JsonProperty("flex")
    public String getFlex() {
        return flex;
    }

    public void setFlex(String flex) {
        this.flex = flex;
    }

    @JsonProperty("publication_type")
    public String getPublication_type() {
        return publication_type;
    }

    public void setPublication_type(String publication_type) {
        this.publication_type = publication_type;
    }
}
