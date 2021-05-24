package uy.pepeganga.meli.service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.pepeganga.meli.service.exceptions.NotFoundException;
import uy.pepeganga.meli.service.exceptions.OrderException;
import uy.pepeganga.meli.service.exceptions.ReportError;


@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler({PGException.class, NotFoundException.class, OrderException.class, ReportError.class})
    protected ResponseEntity<PGException> businessException(PGException exception) {
        return buildPGException(exception);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<PGException> exception(Exception exception) {
        return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<PGException> runtimeException(Exception exception) {
        return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<PGException> buildPGException(PGException exception) {
        if (exception instanceof NotFoundException) {
            NotFoundException notFoundException = (NotFoundException) exception;
            return new ResponseEntity<>(PGException.builder(notFoundException), notFoundException.getHttpStatus());
        } else if (exception instanceof OrderException) {
            OrderException orderException = (OrderException) exception;
            return new ResponseEntity<>(PGException.builder(orderException), orderException.getHttpStatus());
        } else if (exception instanceof ReportError) {
            ReportError reportError = (ReportError) exception;
            return new ResponseEntity<>(PGException.builder(reportError), reportError.getHttpStatus());
        } else
            return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
