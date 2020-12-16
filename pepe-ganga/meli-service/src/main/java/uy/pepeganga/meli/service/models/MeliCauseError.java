package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliCauseError {
    private String department;
    private Integer cause_id;
    private String type;
    private String code;
    private String message;

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("cause_id")
    public Integer getCause_id() {
        return cause_id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCause_id(Integer cause_id) {
        this.cause_id = cause_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
