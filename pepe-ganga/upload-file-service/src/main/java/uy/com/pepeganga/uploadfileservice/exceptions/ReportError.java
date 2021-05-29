package uy.com.pepeganga.uploadfileservice.exceptions;

import org.springframework.http.HttpStatus;
import uy.com.pepeganga.business.common.exceptions.PGException;

public class ReportError extends PGException {

    private final HttpStatus httpStatus;

    public ReportError(String message, HttpStatus httpStatus) {
        super(message, httpStatus.name(), httpStatus.value());
        this.httpStatus = httpStatus;
    }


    public ReportError(String message, HttpStatus httpStatus, String... causes) {
        super(message, httpStatus.name(), httpStatus.value(), causes);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
