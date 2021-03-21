package uy.pepeganga.meli.service.exceptions;

import org.springframework.http.HttpStatus;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.businessutils.exceptions.HttpStatusPGException;

public class OrderException extends PGException implements HttpStatusPGException {

    private final HttpStatus httpStatus;

    public OrderException(String message, HttpStatus httpStatus, String... causes) {
        super(message, httpStatus.name(), httpStatus.value(), causes);
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
