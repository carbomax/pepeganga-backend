package uy.pepeganga.meli.service.models;

import java.io.Serializable;

public class ApiMeliModelException implements Serializable {

    private int code;

    private String responseBody;

    private MeliResponseBodyException exception;

    public ApiMeliModelException(int code, String responseBody, MeliResponseBodyException exception) {
        this.code = code;
        this.responseBody = responseBody;
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public MeliResponseBodyException getException() {
        return exception;
    }

    public void setException(MeliResponseBodyException exception) {
        this.exception = exception;
    }
}
