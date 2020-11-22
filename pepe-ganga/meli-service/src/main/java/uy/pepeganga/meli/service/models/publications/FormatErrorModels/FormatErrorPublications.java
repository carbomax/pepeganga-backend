package uy.pepeganga.meli.service.models.publications.FormatErrorModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormatErrorPublications {
    private String message;
    private String error;
    private Integer status;
    private List<FormatErrorCause> cause;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FormatErrorCause> getCause() {
        return cause;
    }

    public void setCause(List<FormatErrorCause> cause) {
        this.cause = cause;
    }
}
