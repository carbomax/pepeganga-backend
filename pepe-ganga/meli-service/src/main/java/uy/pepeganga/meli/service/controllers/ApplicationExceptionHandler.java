package uy.pepeganga.meli.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.pepeganga.meli.service.exceptions.NotFoundException;


@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<PGException> handlerBusinessException(NotFoundException e){
        return new ResponseEntity<>(ApplicationExceptionHandler.pgExceptionBuilder(e), e.getHttpStatus());
    }

    public static PGException pgExceptionBuilder(PGException exception){
        if(exception instanceof NotFoundException){
            NotFoundException e = (NotFoundException) exception;
            return new PGException(e.getMessage(), e.getError(), e.getStatus(), e.getCauses());
        } else return exception;
    }

}
