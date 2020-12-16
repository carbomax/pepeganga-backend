package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliResponseBodyException implements Serializable {

    private String message;

    private String error;

    private int status;

    private Object[] cause;


    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    @JsonProperty("cause")
    public Object[] getCause() {
        return cause;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCause(Object[] cause) {
        this.cause = cause;
    }
}
