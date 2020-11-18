package uy.pepeganga.meli.service.models.publications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliCodeResponse {
  private Integer status;
  private DMItem body;

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("body")
    public DMItem getBody() {
        return body;
    }
}
