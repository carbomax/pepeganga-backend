package uy.pepeganga.meli.service.models.publications;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeStatusPublicationRequest implements Serializable {

    private String status = "paused";

    public ChangeStatusPublicationRequest() {
        // Do nothing
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
