package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliResponseBodyException implements Serializable {

    private String message;

    private String error;

    private int status;

    private Object[] cause;

    public MeliResponseBodyException() {
    }

    public MeliResponseBodyException(String responseBody) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject objJSON = (JSONObject) jsonParser.parse(responseBody);
        this.message = objJSON.get("message").toString();
        this.error = objJSON.get("error").toString();
        this.status = Integer.parseInt(objJSON.get("status").toString());
    }

    public MeliResponseBodyException(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public MeliResponseBodyException(String message, String error, int status, Object[] cause) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.cause = cause;
    }

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
