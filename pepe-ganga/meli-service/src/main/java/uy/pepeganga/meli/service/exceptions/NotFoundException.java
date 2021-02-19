package uy.pepeganga.meli.service.exceptions;

import org.springframework.http.HttpStatus;
import uy.com.pepeganga.business.common.exceptions.PGException;

public class NotFoundException extends PGException {


    private final HttpStatus httpStatus;

    public NotFoundException(String message, HttpStatus httpStatus, String... causes) {
        super(message, httpStatus.name(), httpStatus.value(), causes);
        this.httpStatus = httpStatus;
    }

    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus.name(), httpStatus.value());
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
