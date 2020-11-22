package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionRequest {
    public static final String SERIALIZED_NAME_PLAIN_TEXT = "plain_text";
    @SerializedName(SERIALIZED_NAME_PLAIN_TEXT)
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
